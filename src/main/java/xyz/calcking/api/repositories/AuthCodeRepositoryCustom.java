package xyz.calcking.api.repositories;

import xyz.calcking.api.documents.AuthCodeDocument;

import java.util.Optional;

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