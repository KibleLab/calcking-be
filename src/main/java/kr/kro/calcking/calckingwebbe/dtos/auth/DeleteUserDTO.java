package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeleteUserDTO {
  @NotBlank(message = "AccessToken이 필요합니다.")
  @JsonProperty("access_token")
  private String accessToken;

  @NotBlank(message = "인증번호를 입력해주세요.")
  @Size(min = 8, max = 8, message = "대소문자, 숫자를 포함한 인증번호 8자리를 입력해주세요.")
  @JsonProperty("verify_string")
  private String verifyString;
}
