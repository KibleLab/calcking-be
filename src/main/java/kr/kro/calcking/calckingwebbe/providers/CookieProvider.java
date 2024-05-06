package kr.kro.calcking.calckingwebbe.providers;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieProvider {
  public void createCookie(
      String name, String value, int maxAge,
      HttpServletResponse response) throws Exception {
    try {
      Cookie cookie = new Cookie(name, value);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setMaxAge(maxAge);
      cookie.setPath("/");
      response.addCookie(cookie);
    } catch (Exception e) {
      throw new Exception("Cookie 생성에 실패했습니다.", e.getCause());
    }
  }

  public Cookie getCookie(String name, HttpServletRequest request) throws Exception {
    try {
      final Cookie[] cookies = request.getCookies();
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name))
          return cookie;
      }
      throw new Exception("해당하는 이름의 Cookie가 존재하지 않습니다.");
    } catch (Exception e) {
      throw new Exception("Cookie 조회에 실패했습니다.", e.getCause());
    }
  }

  public void deleteCookie(Cookie cookie, HttpServletResponse response) throws Exception {
    try {
      cookie.setValue(null);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);
    } catch (Exception e) {
      throw new Exception("Cookie 삭제에 실패했습니다.", e.getCause());
    }
  }
}
