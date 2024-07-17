package xyz.calcking.api.repositories;

import xyz.calcking.api.entities.UserEmailEntity;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserEmailRepository extends JpaRepository<UserEmailEntity, String> {
  // CREATE
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user_email (u_email, is_verified) VALUES (:uEmail, false)", nativeQuery = true)
  public void createUserEmail(String uEmail);

  // READ
  @Query(value = "SELECT * FROM user_email WHERE u_email = :uEmail", nativeQuery = true)
  public Optional<UserEmailEntity> readUserEmail(String uEmail);

  // UPDATE
  @Modifying
  @Transactional
  @Query(value = "UPDATE user_email SET is_verified = :isVerified WHERE u_email = :uEmail", nativeQuery = true)
  public void updateUserEmail(String uEmail, boolean isVerified);

  // DELETE
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_email WHERE u_email = :uEmail", nativeQuery = true)
  public void deleteUserEmail(String uEmail);
}
