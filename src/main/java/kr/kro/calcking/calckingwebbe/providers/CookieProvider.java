package kr.kro.calcking.calckingwebbe.providers;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieProvider {
  // Cookie 생성 메서드
  public void createCookie(
      String name, String value, int maxAge,
      HttpServletResponse response) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setMaxAge(maxAge);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  // Cookie 조회 메서드
  public Cookie getCookie(String name, HttpServletRequest request) {
    final Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name))
          return cookie;
      }
    }
    return null;
  }

  // Cookie 삭제 메서드
  public void deleteCookie(Cookie cookie, HttpServletResponse response) {
    cookie.setValue(null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  // Cookie에서 토큰 추출 메서드
  public String getTokenFromCookie(Cookie cookie) {
    if (cookie != null) {
      return cookie.getValue();
    }
    return null;
  }
}
