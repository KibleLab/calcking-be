package xyz.calcking.api.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.dtos.auth.ReadUserDTO;
import xyz.calcking.api.calckingwebbe.entities.UserEntity;
import xyz.calcking.api.calckingwebbe.providers.CookieProvider;
import xyz.calcking.api.calckingwebbe.providers.TokenProvider;
import xyz.calcking.api.calckingwebbe.repositories.TokenRepository;
import xyz.calcking.api.calckingwebbe.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class SignInService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final CookieProvider cookieProvider;
  private final TokenProvider tokenProvider;

  // POST (/sign-in)
  public ResponseEntity<Map<String, Object>> signIn(ReadUserDTO readUserDTO, HttpServletResponse response) {
    // ID 및 PW 검증
    Optional<UserEntity> user = userRepository.readUserByUID(readUserDTO.getUID());
    if (user.isEmpty())
      return responseMap("존재하지 않는 사용자 입니다!", HttpStatus.BAD_REQUEST);
    else if (!isPasswordMatch(readUserDTO.getUPW(), user.get().getUPW()))
      return responseMap("비밀번호가 일치하지 않습니다!", HttpStatus.BAD_REQUEST);

    // Token Payload 생성
    Map<String, Object> payload = new HashMap<>();
    payload.put("ID", user.get().getUID());

    // RefreshToken 발급
    Date refreshTokenExpireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 15));
    Optional<String> refreshToken = tokenProvider.issueToken(payload, refreshTokenExpireAt);
    if (refreshToken.isEmpty())
      return responseMap("토큰 발급 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else {
      tokenRepository.createToken(refreshToken.get(), refreshTokenExpireAt, user.get().getUID());
      cookieProvider.createCookie("refresh_token", refreshToken.get(), 60 * 60 * 24 * 15, response);
    }

    // AccessToken 발급
    Date accessTokenExpireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 15));
    Optional<String> accessToken = tokenProvider.issueToken(payload, accessTokenExpireAt);
    if (accessToken.isEmpty())
      return responseMap("토큰 발급 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else
      response.addHeader("Authorization", "Bearer " + accessToken.get().toString());

    // 성공 응답
    return responseMap("로그인 성공!", HttpStatus.OK);
  }

  // PW 일치 여부 확인 은닉 메서드
  private boolean isPasswordMatch(String rawPW, String encodedPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(rawPW, encodedPW);
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}
