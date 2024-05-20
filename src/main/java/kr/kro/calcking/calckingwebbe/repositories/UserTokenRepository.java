package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import kr.kro.calcking.calckingwebbe.entities.UserTokenEntity;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, Integer> {
  // Create
  @Modifying
  @Transactional
  @Query(value = "INSERT INTO user_token (u_token, u_id) VALUES (:refreshToken, :uID)", nativeQuery = true)
  public void createUserToken(String uID, String refreshToken);

  // Read
  @Query(value = "SELECT * FROM user_token WHERE u_id = :uID", nativeQuery = true)
  public Optional<UserTokenEntity> readUserTokenByUID(String uID);

  @Query(value = "SELECT * FROM user_token WHERE u_token = :refreshToken", nativeQuery = true)
  public Optional<UserTokenEntity> readUserTokenByRefreshToken(String refreshToken);

  // Update
  @Modifying
  @Transactional
  @Query(value = "UPDATE user_token SET u_token = :refreshToken WHERE u_id = :uID", nativeQuery = true)
  public void updateUserTokenByUID(String uID, String refreshToken);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user_token SET u_token = :nextRefreshToken WHERE u_token = :prevRefreshToken", nativeQuery = true)
  public void updateUserTokenByPrevRefreshToken(String prevRefreshToken, String nextRefreshToken);

  // Delete
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_token WHERE u_id = :uID", nativeQuery = true)
  public void deleteUserTokenByUID(String uID);
}
