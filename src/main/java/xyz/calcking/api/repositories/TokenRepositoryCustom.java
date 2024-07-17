package xyz.calcking.api.repositories;

import xyz.calcking.api.documents.TokenDocument;

import java.util.Date;
import java.util.Optional;

public interface TokenRepositoryCustom {
  // CREATE
  public void createToken(String refreshToken, Date expireAt, String uID);

  // READ
  public Optional<TokenDocument> readToken(String refreshToken);

  // UPDATE
  public void updateToken(String prevRefreshToken, String nextRefreshToken, Date expireAt);

  // DELETE
  public void deleteToken(String refreshToken);

  public void deleteTokenByUID(String uID);
}
