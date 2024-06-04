package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import kr.kro.calcking.calckingwebbe.documents.TokenDocument;

public class TokenRepositoryCustomImpl implements TokenRepositoryCustom {
  @Autowired
  private MongoTemplate mongoTemplate;

  // CREATE
  @Override
  public void createToken(String refreshToken, Date expireAt, String uID) {
    TokenDocument tokenDocument = new TokenDocument();
    tokenDocument.setToken(refreshToken);
    tokenDocument.setExpireAt(expireAt);
    tokenDocument.setUID(uID);
    mongoTemplate.insert(tokenDocument);
  }

  // READ
  @Override
  public Optional<TokenDocument> readToken(String refreshToken) {
    Query query = new Query(Criteria.where("token").is(refreshToken));
    TokenDocument tokenDocument = mongoTemplate.findOne(query, TokenDocument.class);
    return Optional.ofNullable(tokenDocument);
  }

  // UPDATE
  @Override
  public void updateToken(String prevRefreshToken, String nextRefreshToken, Date expireAt) {
    Query query = new Query(Criteria.where("token").is(prevRefreshToken));
    Update update = new Update().set("token", nextRefreshToken).set("expire_at", expireAt);
    mongoTemplate.updateFirst(query, update, TokenDocument.class);
  }

  // DELETE
  @Override
  public void deleteToken(String refreshToken) {
    Query query = new Query(Criteria.where("token").is(refreshToken));
    mongoTemplate.remove(query, TokenDocument.class);
  }

  @Override
  public void deleteTokenByUID(String uID) {
    Query query = new Query(Criteria.where("u_id").is(uID));
    mongoTemplate.remove(query, TokenDocument.class);
  }
}
