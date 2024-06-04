package kr.kro.calcking.calckingwebbe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "user")
public class UserEntity {
  @Id
  @Column(name = "u_id", length = 20)
  @JsonProperty("u_id")
  private String uID;

  @NotNull
  @Column(name = "u_pw")
  @JsonProperty("u_pw")
  private String uPW;

  @NotNull
  @Column(name = "u_name", length = 10)
  @JsonProperty("u_name")
  private String uName;

  @NotNull
  @Column(name = "u_phone", length = 11)
  @JsonProperty("u_phone")
  private String uPhone;

  @NotNull
  @OneToOne
  @JoinColumn(name = "u_email", referencedColumnName = "u_email")
  private UserEmailEntity userEmailEntity;

  @NotNull
  @Column(name = "u_birth", length = 8)
  @JsonProperty("u_birth")
  private String uBirth;

  @NotNull
  @OneToOne
  @JoinColumn(name = "u_role_id", referencedColumnName = "u_role_id")
  private UserRoleEntity userRoleEntity;
}
