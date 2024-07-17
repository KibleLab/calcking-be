package xyz.calcking.api.repositories;

import xyz.calcking.api.entities.UserEntity;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, String> {
  // Create
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user (u_id, u_pw, u_name, u_birth, u_phone, u_email, u_role_id) VALUES (:uID, :uPW, :uName, :uBirth, :uPhone, :uEmail, :uRoleID)", nativeQuery = true)
  public void createUser(String uID, String uPW, String uName, String uBirth, String uPhone, String uEmail,
      int uRoleID);

  // Read
  @Query(value = "SELECT * FROM user WHERE u_id = :uID", nativeQuery = true)
  public Optional<UserEntity> readUserByUID(String uID);

  @Query(value = "SELECT * FROM user WHERE u_email = :uEmail", nativeQuery = true)
  public Optional<UserEntity> readUserByUEmail(String uEmail);

  // Delete
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user WHERE u_id = :uID", nativeQuery = true)
  public void deleteUserByUID(String uID);
}
