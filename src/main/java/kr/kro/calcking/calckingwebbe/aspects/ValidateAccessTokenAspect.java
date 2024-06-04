package kr.kro.calcking.calckingwebbe.aspects;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.calcking.calckingwebbe.annotations.ValidateAccessToken;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.TokenProvider;
import kr.kro.calcking.calckingwebbe.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;

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

    // 토큰 추출
    String accessToken = request.getHeader("Authorization").substring(7);
    String refreshToken = cookieProvider.getTokenFromCookie(cookieProvider.getCookie("refresh_token", request));
    Optional<Date> accessTokenIssuedAt = tokenProvider.getIssuedAtFromToken(accessToken);
    Optional<Date> refreshTokenIssuedAt = tokenProvider.getIssuedAtFromToken(refreshToken);
    if (accessTokenIssuedAt.isEmpty() || refreshTokenIssuedAt.isEmpty())
      return responseMap("토큰이 유효하지 않습니다!", HttpStatus.UNAUTHORIZED);

    // RefreshToken 검증 로직
    if (tokenRepository.readToken(refreshToken).isEmpty())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.UNAUTHORIZED);

    // AccessToken 검증 로직
    if (tokenProvider.isExpiredToken(accessToken).get().booleanValue())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.UNAUTHORIZED);
    else if (!accessTokenIssuedAt.equals(refreshTokenIssuedAt))
      return responseMap("토큰 발급 시간이 일치하지 않습니다!", HttpStatus.UNAUTHORIZED);

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