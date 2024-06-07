package xyz.calcking.api.calckingwebbe.interceptors;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MDCInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    MDC.put("httpMethod", request.getMethod());
    MDC.put("requestUrl", request.getRequestURI());
    MDC.put("httpVersion", request.getHeader("X-Forwarded-Proto").toUpperCase());
    MDC.put("clientIp", request.getHeader("X-Real-IP"));
    MDC.put("userAgent", request.getHeader("User-Agent"));

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
