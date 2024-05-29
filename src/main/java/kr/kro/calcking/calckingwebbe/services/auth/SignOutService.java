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
public class SignOutService {
  private final UserTokenRepository userTokenRepository;
  private final CookieProvider cookieProvider;
  private final JWTProvider jwtProvider;

  // POST (/sign-out)
  public ResponseEntity<Map<String, Object>> signOut(
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();

    // 로그아웃 로직
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    userTokenRepository
        .deleteUserTokenByUID(
            jwtProvider.getUIDFromAccessToken(request.getHeader("Authorization").substring(7)));
    cookieProvider.deleteCookie(refreshCookie, response);

    // JSON 응답 로직
    responseMap.put("message", "로그아웃 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.OK));
    return ResponseEntity.status(HttpStatus.OK).body(responseMap);
  }
}
