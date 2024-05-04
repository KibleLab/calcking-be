package kr.kro.calcking.calckingwebbe.providers;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.util.StandardCharset;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTProvider {
  @Value("${jwt.secret}")
  private String SECRET_KEY;

  public String createAccessToken(String uID) throws JwtException {
    try {
      byte[] keyBytes = SECRET_KEY.getBytes(StandardCharset.UTF_8);
      Key key = Keys.hmacShaKeyFor(keyBytes);
      return Jwts.builder()
          .header().add("type", "jwt").and()
          .claim("ID", uID)
          .issuedAt(new Date())
          .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
          .signWith(key)
          .compact();
    } catch (JwtException e) {
      throw new JwtException("AccessToken 생성에 실패했습니다.", e.getCause());
    }
  }

  public String createRefreshToken(String uID) throws JwtException {
    try {
      byte[] keyBytes = SECRET_KEY.getBytes(StandardCharset.UTF_8);
      Key key = Keys.hmacShaKeyFor(keyBytes);
      return Jwts.builder()
          .header().add("type", "jwt").and()
          .claim("ID", uID)
          .issuedAt(new Date())
          .expiration(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30)))
          .signWith(key)
          .compact();
    } catch (JwtException e) {
      throw new JwtException("RefreshToken 생성에 실패했습니다.", e.getCause());
    }
  }
}
