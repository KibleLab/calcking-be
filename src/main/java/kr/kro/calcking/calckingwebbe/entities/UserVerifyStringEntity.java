package kr.kro.calcking.calckingwebbe.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "user_verify_string")
public class UserVerifyStringEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "u_verify_string_id")
  @JsonProperty("u_verify_string_id")
  private int uVerifyStringID;

  @Column(name = "u_verify_string")
  @JsonProperty("verify_string")
  private String uVerifyString;

  @Column(name = "u_verify_string_expire_at")
  @JsonProperty("u_verify_string_expire_at")
  private Date uVerfyStringExpireAt;

  @NotNull
  @OneToOne
  @JoinColumn(name = "u_id", referencedColumnName = "u_id")
  private UserEntity userEntity;

}
