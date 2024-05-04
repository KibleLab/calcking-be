package kr.kro.calcking.calckingwebbe.providers;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieProvider {

  public Cookie createCookie(String name, String value, int maxAge) throws Exception {
    try {
      Cookie cookie = new Cookie(name, value);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setMaxAge(maxAge);
      cookie.setPath("/");
      return cookie;
    } catch (Exception e) {
      throw new Exception("Cookie 생성에 실패했습니다.", e.getCause());
    }
  }

  public Cookie getCookie(HttpServletRequest request, String name) {
    final Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        return cookie;
      }
    }
    return null;
  }

  public Cookie deleteCookie(String name) throws Exception {
    try {
      Cookie cookie = new Cookie(name, null);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      return cookie;
    } catch (Exception e) {
      throw new Exception("Cookie 삭제에 실패했습니다.", e.getCause());
    }
  }
}
