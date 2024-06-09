package xyz.calcking.api.calckingwebbe.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "user_role")
public class UserRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "u_role_id")
  @JsonProperty("u_role_id")
  private int uRoleID;

  @Column(name = "u_role_name")
  @JsonProperty("u_role_name")
  private String uRoleName;
}
