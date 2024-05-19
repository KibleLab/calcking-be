package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAccessTokenDTO;
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
      ReadAccessTokenDTO readAccessTokenDTO,
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();

    // AccessToken 검증 로직
    String accessToken = readAccessTokenDTO.getAccessToken();
    String refreshToken = cookieProvider.getTokenFromCookie(cookieProvider.getCookie("refresh_token", request));
    Date accessTokenIssuedAt = jwtProvider.getIssuedAtFromAccessToken(accessToken);
    Date refreshTokenIssuedAt = jwtProvider.getIssuedAtFromRefreshToken(refreshToken);
    if (jwtProvider.isExpiredAccessToken(readAccessTokenDTO.getAccessToken())) {
      responseMap.put("message", "AccessToken이 만료되었습니다.");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    } else if (!accessTokenIssuedAt.equals(refreshTokenIssuedAt)) {
      responseMap.put("message", "AccessToken이 유효하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    }

    // 로그아웃 로직
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    userTokenRepository.deleteUserTokenByUID(jwtProvider.getUIDFromAccessToken(readAccessTokenDTO.getAccessToken()));
    cookieProvider.deleteCookie(refreshCookie, response);

    // JSON 응답 로직
    responseMap.put("message", "로그아웃 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.OK));
    return ResponseEntity.status(HttpStatus.OK).body(responseMap);
  }
}
