package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Optional;

import kr.kro.calcking.calckingwebbe.documents.AuthCodeDocument;

public interface AuthCodeRepositoryCustom {
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