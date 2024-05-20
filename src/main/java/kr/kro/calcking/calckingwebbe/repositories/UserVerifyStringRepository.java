package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import kr.kro.calcking.calckingwebbe.entities.UserVerifyStringEntity;

public interface UserVerifyStringRepository extends JpaRepository<UserVerifyStringEntity, Integer> {
  // Create
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user_verify_string (u_verify_string, u_verify_string_expire_at, u_id) VALUES (:verifyString, :expireAt, :uID)", nativeQuery = true)
  public void createUserVerifyString(String uID, String verifyString, Date expireAt);

  // Read
  @Query(value = "SELECT * FROM user_verify_string WHERE u_id = :uID", nativeQuery = true)
  public Optional<UserVerifyStringEntity> readUserVerifyStringByUID(String uID);

  // Delete
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_verify_string WHERE u_id = :uID", nativeQuery = true)
  public void deleteUserVerifyStringByUID(String uID);
}
