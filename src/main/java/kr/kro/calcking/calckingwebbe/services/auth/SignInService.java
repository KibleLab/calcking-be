package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadUserDTO;
import kr.kro.calcking.calckingwebbe.entities.UserEntity;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.JWTProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import kr.kro.calcking.calckingwebbe.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {
  private final UserRepository userRepository;
  private final UserTokenRepository userTokenRepository;
  private final JWTProvider jwtProvider;
  private final CookieProvider cookieProvider;

  // POST (/sign-in)
  public ResponseEntity<Map<String, Object>> signIn(ReadUserDTO readUserDTO, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();
    Optional<UserEntity> user = userRepository.readUserByUID(readUserDTO.getUID());

    // ID 및 PW 검증 로직
    if (user.isEmpty()) {
      responseMap.put("message", "ID가 잘못되었거나 존재하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    } else if (!isPasswordMatch(readUserDTO.getUPW(), user.get().getUPW())) {
      responseMap.put("message", "비밀번호가 잘못되었습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

    // RefreshToken 발급 로직
    String refreshToken = jwtProvider.issueRefreshToken(user.get().getUID());
    userTokenRepository.deleteUserTokenByUID(user.get().getUID());
    userTokenRepository.createUserToken(user.get().getUID(), refreshToken);
    cookieProvider.createCookie("refresh_token", refreshToken, 60 * 60 * 24 * 15, response);

    // AccessToken 발급 로직
    String accessToken = jwtProvider.issueAccessToken(user.get().getUID());

    // JSON 응답 로직
    responseMap.put("access_token", accessToken);
    responseMap.put("message", "로그인 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.OK));
    return ResponseEntity.status(HttpStatus.OK).body(responseMap);
  }

  // PW 일치 여부 확인 은닉 메서드
  private boolean isPasswordMatch(String rawPW, String encodedPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(rawPW, encodedPW);
  }
}
