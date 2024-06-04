package kr.kro.calcking.calckingwebbe.providers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  // Token 발급 메서드
  public Optional<String> issueToken(Map<String, Object> payload, Date expireAt) {
    try {
      Map<String, Object> headerMap = new HashMap<>();
      headerMap.put("type", "jwt");
      String token = JWT.create()
          .withHeader(headerMap)
          .withPayload(payload)
          .withIssuedAt(new Date())
          .withExpiresAt(expireAt)
          .sign(Algorithm.HMAC512(SECRET_KEY));
      return Optional.of(token);
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }

  // Token 재발급 메서드
  public Optional<String> reissueToken(String refreshToken, Date expireAt) {
    try {
      Optional<Map<String, Object>> payload = getPayloadFromToken(refreshToken);
      if (payload.isPresent())
        return issueToken(payload.get(), expireAt);
      else
        return Optional.empty();
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }

  // Token issuedAt 추출 메서드
  public Optional<Date> getIssuedAtFromToken(String token) {
    try {
      Date issuedAt = JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(token)
          .getIssuedAt();
      return Optional.of(issuedAt);
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }

  // Token 만료 여부 확인 메서드
  public Optional<Boolean> isExpiredToken(String token) {
    try {
      boolean isExpired = JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(token)
          .getExpiresAt()
          .before(new Date());
      return Optional.of(isExpired);
    } catch (JWTVerificationException e) {
      return Optional.of(false);
    }
  }

  // Token Payload 추출 메서드
  public Optional<Map<String, Object>> getPayloadFromToken(String token) {
    try {
      Map<String, Object> payload = JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(token)
          .getClaims()
          .entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().as(Object.class)));
      return Optional.of(payload);
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }
}
