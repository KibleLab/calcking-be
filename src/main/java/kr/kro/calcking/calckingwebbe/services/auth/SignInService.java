package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.dtos.auth.SignInDTO;
import kr.kro.calcking.calckingwebbe.entities.UserEntity;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.JWTProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;

@Service
@RequiredArgsConstructor
public class SignInService {
  private final UserRepository userRepository;
  private final JWTProvider jwtProvider;
  private final CookieProvider cookieProvider;

  public ResponseEntity<?> signIn(SignInDTO signInDTO, HttpServletResponse response) throws JwtException, Exception {
    Optional<UserEntity> user = userRepository.findUserByID(signInDTO.getUID());

    if (user.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID가 잘못되었거나 존재하지 않습니다!");
    }

    if (!isPasswordMatch(signInDTO.getUPW(), user.get().getUPW())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 잘못되었습니다!");
    }

    try {
      String accessToken = jwtProvider.createAccessToken(user.get().getUID());
      String refreshToken = jwtProvider.createRefreshToken(user.get().getUID());
      userRepository.updateUserToken(user.get().getUID(), refreshToken);
      addCookiesToResponse(accessToken, refreshToken, response);
    } catch (JwtException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }

    return ResponseEntity.status(HttpStatus.OK).body("로그인 성공!");
  }

  private boolean isPasswordMatch(String rawPW, String encodedPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(rawPW, encodedPW);
  }

  private void addCookiesToResponse(String accessToken, String refreshToken, HttpServletResponse response)
      throws Exception {
    Cookie accessCookie = cookieProvider.createCookie("accessToken", accessToken, 60 * 10);
    Cookie refreshCookie = cookieProvider.createCookie("refreshToken", refreshToken, 60 * 60 * 24 * 30);
    response.addCookie(accessCookie);
    response.addCookie(refreshCookie);
  }
}
