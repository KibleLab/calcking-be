package xyz.calcking.api.aspects;

import xyz.calcking.api.annotations.ValidateAccessToken;
import xyz.calcking.api.providers.CookieProvider;
import xyz.calcking.api.providers.TokenProvider;
import xyz.calcking.api.repositories.TokenRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateAccessTokenAspect {
  private final TokenRepository tokenRepository;
  private final CookieProvider cookieProvider;
  private final TokenProvider tokenProvider;

  @Around("@annotation(validAccessToken)") // @ValidateAccessToken이 붙은 메서드에서만 실행
  public Object validAccessToken(
      ProceedingJoinPoint joinPoint, ValidateAccessToken validAccessToken)
      throws Throwable {
    // HttpServletRequest 추출
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
    HttpServletRequest request = servletRequestAttributes.getRequest();

    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    Cookie accessCookie = cookieProvider.getCookie("access_token", request);
    // refreshCookie가 존재할 경우
    if (refreshCookie != null) {
      String refreshToken = cookieProvider.getTokenFromCookie(refreshCookie);

      // RefreshToken 발급 시간 확인
      Optional<Date> refreshTokenIssuedAt = tokenProvider.getIssuedAtFromToken(refreshToken);
      if (refreshTokenIssuedAt.isEmpty())
        return responseMap("토큰이 유효하지 않습니다!", HttpStatus.UNAUTHORIZED);

      // DB에 RefreshToken 존재 여부 확인
      if (tokenRepository.readToken(refreshToken).isEmpty())
        return responseMap("토큰이 존재하지 않습니다!", HttpStatus.UNAUTHORIZED);

      // accessCookie가 존재할 경우
      if (accessCookie != null) {
        String accessToken = cookieProvider.getTokenFromCookie(accessCookie);

        // AccessToken 발급 시간 확인
        Optional<Date> accessTokenIssuedAt = tokenProvider.getIssuedAtFromToken(accessToken);
        if (accessTokenIssuedAt.isEmpty() || refreshTokenIssuedAt.isEmpty())
          return responseMap("토큰이 유효하지 않습니다!", HttpStatus.UNAUTHORIZED);

        // Token 발급 시간 일치 여부 확인
        if (accessTokenIssuedAt.get().equals(refreshTokenIssuedAt.get()))
          return responseMap("토큰이 발급 시간이 일치하지 않습니다!", HttpStatus.UNAUTHORIZED);

        // AccessToken 만료 여부 확인
        if (tokenProvider.isExpiredToken(accessToken).get().booleanValue())
          return responseMap("토큰이 만료되었습니다!", HttpStatus.UNAUTHORIZED);
      } else
        return responseMap("토큰이 존재하지 않습니다!", HttpStatus.UNAUTHORIZED);
    } else
      return responseMap("토큰이 존재하지 않습니다!", HttpStatus.UNAUTHORIZED);

    return joinPoint.proceed();
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}