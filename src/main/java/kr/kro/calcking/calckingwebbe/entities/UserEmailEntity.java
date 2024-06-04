package kr.kro.calcking.calckingwebbe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "user_email")
public class UserEmailEntity {
  @Id
  @Column(name = "u_email")
  @JsonProperty("u_email")
  private String uEmail;

  @NotNull
  @Column(name = "is_verified")
  @JsonProperty("is_verified")
  private boolean isVerified;

  @OneToOne(mappedBy = "userEmailEntity")
  private UserEntity userEntity;
}
