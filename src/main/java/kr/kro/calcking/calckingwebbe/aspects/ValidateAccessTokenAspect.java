package kr.kro.calcking.calckingwebbe.aspects;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.calcking.calckingwebbe.annotations.ValidateAccessToken;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAccessTokenDTO;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.JWTProvider;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateAccessTokenAspect {
  private final CookieProvider cookieProvider;
  private final JWTProvider jwtProvider;

  @Around("@annotation(validAccessToken)")
  public Object validAccessToken(ProceedingJoinPoint joinPoint, ValidateAccessToken validAccessToken)
      throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();

    Map<String, Object> responseMap = new HashMap<>();

    String accessToken = ((ReadAccessTokenDTO) joinPoint.getArgs()[0]).getAccessToken();
    String refreshToken = cookieProvider.getTokenFromCookie(cookieProvider.getCookie("refresh_token", request));
    Date accessTokenIssuedAt = jwtProvider.getIssuedAtFromAccessToken(accessToken);
    Date refreshTokenIssuedAt = jwtProvider.getIssuedAtFromRefreshToken(refreshToken);

    if (jwtProvider.isExpiredAccessToken(accessToken)) {
      responseMap.put("message", "토큰이 유효하지 않거나 만료되었습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    } else if (!accessTokenIssuedAt.equals(refreshTokenIssuedAt)) {
      responseMap.put("message", "토큰 발급 시간이 일치하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    }

    return joinPoint.proceed();
  }
}