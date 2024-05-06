package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import kr.kro.calcking.calckingwebbe.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
  @Query(value = "SELECT * FROM user WHERE u_id = :uID", nativeQuery = true)
  public Optional<UserEntity> findUserByID(String uID);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user (u_id, u_pw, u_name, u_birth, u_phone, u_email) VALUES (:uID, :uPW, :uName, :uBirth, :uPhone, :uEmail)", nativeQuery = true)
  public void createUser(String uID, String uPW, String uName, String uBirth, String uPhone, String uEmail);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET u_token = :uToken WHERE u_id = :uID", nativeQuery = true)
  public void updateUserToken(String uID, String uToken);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET u_token = NULL WHERE u_id = :uID", nativeQuery = true)
  public void deleteUserToken(String uID);
}
