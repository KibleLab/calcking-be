package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignOutDTO {
  @NotBlank(message = "ID를 입력해주세요.")
  @Size(min = 4, max = 20, message = "ID는 4자 이상 20자 이하로 입력해주세요.")
  @JsonProperty("u_id")
  private String uID;
}
