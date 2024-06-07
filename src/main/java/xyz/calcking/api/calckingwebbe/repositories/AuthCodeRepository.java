package xyz.calcking.api.calckingwebbe.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.calcking.api.calckingwebbe.documents.AuthCodeDocument;

public interface AuthCodeRepository extends MongoRepository<AuthCodeDocument, String>, AuthCodeRepositoryCustom {
  // CREATE
  public void createAuthCodeByUID(String authCode, String uID);

  public void createAuthCodeByUEmail(String authCode, String uEmail);

  // READ
  public Optional<AuthCodeDocument> readAuthCodeByUID(String authCode);

  public Optional<AuthCodeDocument> readAuthCodeByUEmail(String authCode);

  // DELETE
  public void deleteAuthCodeByUID(String authCode);

  public void deleteAuthCodeByUEmail(String authCode);
}
