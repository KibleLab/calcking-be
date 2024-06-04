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
import kr.kro.calcking.calckingwebbe.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignOutService {
  private final TokenRepository tokenRepository;
  private final CookieProvider cookieProvider;

  // POST (/sign-out)
  public ResponseEntity<Map<String, Object>> signOut(HttpServletRequest request, HttpServletResponse response) {
    // 로그아웃
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    String refreshToken = cookieProvider.getTokenFromCookie(refreshCookie);
    if (refreshCookie != null) {
      cookieProvider.deleteCookie(refreshCookie, response);
      tokenRepository.deleteToken(refreshToken);
    }

    // 성공 응답
    return responseMap("로그아웃 성공!", HttpStatus.OK);
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}
