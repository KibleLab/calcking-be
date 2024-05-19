package kr.kro.calcking.calckingwebbe.entities;

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
@Entity(name = "user_token")
public class UserTokenEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "u_token_id")
  @JsonProperty("u_token_id")
  private int uTokenID;

  @Column(name = "u_token")
  @JsonProperty("u_token")
  private String uToken;

  @NotNull
  @OneToOne
  @JoinColumn(name = "u_id", referencedColumnName = "u_id")
  private UserEntity userEntity;
}
