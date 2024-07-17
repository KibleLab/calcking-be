package xyz.calcking.api.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class MDCInterceptor implements HandlerInterceptor {
  private final Environment environment;

  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    boolean isDevProfile = environment.acceptsProfiles(Profiles.of("dev"));
    boolean isProdProfile = environment.acceptsProfiles(Profiles.of("prod"));

    MDC.put("httpMethod", request.getMethod());
    MDC.put("requestUrl", request.getRequestURI());
    MDC.put("userAgent", request.getHeader("User-Agent"));
    if (isDevProfile) {
      MDC.put("httpVersion", request.getProtocol());
      MDC.put("clientIp", request.getRemoteAddr());
    } else if (isProdProfile) {
      MDC.put("httpVersion", request.getHeader("X-Forwarded-Proto"));
      MDC.put("clientIp", request.getHeader("X-Real-IP"));
    }

    return true;
  }

  @Override
  public void afterCompletion(
      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler,
      @Nullable Exception ex) {
    MDC.remove("httpMethod");
    MDC.remove("requestUrl");
    MDC.remove("httpVersion");
    MDC.remove("clientIp");
    MDC.remove("userAgent");
    MDC.clear();
  }
}
