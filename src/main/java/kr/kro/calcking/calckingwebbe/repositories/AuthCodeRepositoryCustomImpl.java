package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import kr.kro.calcking.calckingwebbe.documents.AuthCodeDocument;

public class AuthCodeRepositoryCustomImpl implements AuthCodeRepositoryCustom {
  @Autowired
  private MongoTemplate mongoTemplate;

  // CREATE
  @Override
  public void createAuthCodeByUID(String authCode, String uID) {
    AuthCodeDocument authCodeDocument = new AuthCodeDocument();
    authCodeDocument.setAuthCode(authCode);
    authCodeDocument.setExpireAt(new Date(System.currentTimeMillis() + 1000 * 60 * 3));
    authCodeDocument.setUID(uID);
    mongoTemplate.save(authCodeDocument);
  }

  @Override
  public void createAuthCodeByUEmail(String authCode, String uEmail) {
    AuthCodeDocument authCodeDocument = new AuthCodeDocument();
    authCodeDocument.setAuthCode(authCode);
    authCodeDocument.setExpireAt(new Date(System.currentTimeMillis() + 1000 * 60 * 3));
    authCodeDocument.setUEmail(uEmail);
    mongoTemplate.save(authCodeDocument);
  }

  // READ
  @Override
  public Optional<AuthCodeDocument> readAuthCodeByUID(String uID) {
    Query query = new Query(Criteria.where("u_id").is(uID));
    AuthCodeDocument authCodeDocument = mongoTemplate.findOne(query, AuthCodeDocument.class);
    return Optional.ofNullable(authCodeDocument);
  }

  @Override
  public Optional<AuthCodeDocument> readAuthCodeByUEmail(String uEmail) {
    Query query = new Query(Criteria.where("u_email").is(uEmail));
    AuthCodeDocument authCodeDocument = mongoTemplate.findOne(query, AuthCodeDocument.class);
    return Optional.ofNullable(authCodeDocument);
  }

  // DELETE
  @Override
  public void deleteAuthCodeByUID(String uID) {
    Query query = new Query(Criteria.where("u_id").is(uID));
    mongoTemplate.remove(query, AuthCodeDocument.class);
  }

  @Override
  public void deleteAuthCodeByUEmail(String uEmail) {
    Query query = new Query(Criteria.where("u_email").is(uEmail));
    mongoTemplate.remove(query, AuthCodeDocument.class);
  }
}
