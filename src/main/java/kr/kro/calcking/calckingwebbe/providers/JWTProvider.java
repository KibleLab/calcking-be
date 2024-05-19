package kr.kro.calcking.calckingwebbe.providers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTProvider {
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  // AccessToken 발급 메서드
  public String issueAccessToken(String uID) {
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("type", "jwt");
    return JWT.create()
        .withHeader(headerMap)
        .withClaim("ID", uID)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000L * 60 * 10)))
        .sign(Algorithm.HMAC512(SECRET_KEY));
  }

  // RefreshToken 발급 메서드
  public String issueRefreshToken(String uID) {
    Map<String, Object> headerMap = new HashMap<>();
    headerMap.put("type", "jwt");
    return JWT.create()
        .withHeader(headerMap)
        .withClaim("ID", uID)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 15)))
        .sign(Algorithm.HMAC512(SECRET_KEY));
  }

  // AccessToken 재발급 메서드
  public String reissueAccessToken(String refreshToken) {
    String uID = JWT.require(Algorithm.HMAC512(SECRET_KEY))
        .build()
        .verify(refreshToken)
        .getClaim("ID")
        .asString();
    return issueAccessToken(uID);
  }

  // RefreshToken 재발급 메서드
  public String reissueRefreshToken(String refreshToken) {
    String uID = JWT.require(Algorithm.HMAC512(SECRET_KEY))
        .build()
        .verify(refreshToken)
        .getClaim("ID")
        .asString();
    return issueRefreshToken(uID);
  }

  // AccessToken issuedAt 추출 메서드
  public Date getIssuedAtFromAccessToken(String accessToken) {
    try {
      return JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(accessToken)
          .getIssuedAt();
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  // RefreshToken issuedAt 추출 메서드
  public Date getIssuedAtFromRefreshToken(String refreshToken) {
    try {
      return JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(refreshToken)
          .getIssuedAt();
    } catch (JWTVerificationException e) {
      return null;
    }
  }

  // AccessToken 만료 여부 확인 메서드
  public boolean isExpiredAccessToken(String accessToken) {
    try {
      return JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(accessToken)
          .getExpiresAt()
          .before(new Date());
    } catch (JWTVerificationException e) {
      return true;
    }
  }

  // RefreshToken 만료 여부 확인 메서드
  public boolean isExpiredRefreshToken(String refreshToken) {
    try {
      return JWT.require(Algorithm.HMAC512(SECRET_KEY))
          .build()
          .verify(refreshToken)
          .getExpiresAt()
          .before(new Date());
    } catch (JWTVerificationException e) {
      return true;
    }
  }

  // AccessToken UID 추출 메서드
  public String getUIDFromAccessToken(String accessToken) {
    return JWT.require(Algorithm.HMAC512(SECRET_KEY))
        .build()
        .verify(accessToken)
        .getClaim("ID")
        .asString();
  }
}
