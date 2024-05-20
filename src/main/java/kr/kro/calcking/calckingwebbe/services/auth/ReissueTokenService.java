package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.JWTProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {
  private final UserTokenRepository userTokenRepository;
  private final CookieProvider cookieProvider;
  private final JWTProvider jwtProvider;

  // POST (/reissue-token)
  public ResponseEntity<Map<String, Object>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    String prevRefreshToken = cookieProvider.getTokenFromCookie(refreshCookie);

    // RefreshToken 검증 로직
    if (userTokenRepository.readUserTokenByRefreshToken(prevRefreshToken).isEmpty()) {
      responseMap.put("message", "RefreshToken이 유효하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    }

    // RefreshToken 재발급 로직
    String nextRefreshToken = jwtProvider.reissueRefreshToken(prevRefreshToken);
    userTokenRepository.updateUserTokenByPrevRefreshToken(prevRefreshToken, nextRefreshToken);
    cookieProvider.createCookie("refresh_token", nextRefreshToken, 60 * 60 * 24 * 15, response);

    // AccessToken 재발급 로직
    String nextAccessToken = jwtProvider.reissueAccessToken(nextRefreshToken);

    // JSON 응답 로직
    responseMap.put("access_token", nextAccessToken);
    responseMap.put("message", "토큰 재발급 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }
}
