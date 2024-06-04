package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.TokenProvider;
import kr.kro.calcking.calckingwebbe.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final TokenRepository tokenRepository;
  private final CookieProvider cookieProvider;
  private final TokenProvider tokenProvider;

  // POST (/reissue-token)
  public ResponseEntity<Map<String, Object>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    String prevRefreshToken = cookieProvider.getTokenFromCookie(refreshCookie);

    // RefreshToken 검증 로직
    if (tokenRepository.readToken(prevRefreshToken).isEmpty())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.UNAUTHORIZED);

    // RefreshToken 재발급 로직
    Date refreshTokenExpireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 15));
    Optional<String> nextRefreshToken = tokenProvider.reissueToken(prevRefreshToken, refreshTokenExpireAt);
    if (nextRefreshToken.isEmpty())
      return responseMap("토큰 재발급 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else {
      tokenRepository.updateToken(prevRefreshToken, nextRefreshToken.get().toString(), refreshTokenExpireAt);
      cookieProvider.createCookie("refresh_token", nextRefreshToken.get().toString(), 60 * 60 * 24 * 15, response);
    }

    // AccessToken 재발급 로직
    Date accessTokenExpireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 15));
    Optional<String> nextAccessToken = tokenProvider.reissueToken(nextRefreshToken.get(), accessTokenExpireAt);
    if (nextAccessToken.isEmpty())
      return responseMap("토큰 재발급 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else
      response.addHeader("Authorization", "Bearer " + nextAccessToken.get().toString());

    // JSON 응답 로직
    return responseMap("토큰 재발급 성공!", HttpStatus.CREATED);
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}
