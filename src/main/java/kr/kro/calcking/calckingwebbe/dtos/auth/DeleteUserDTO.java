package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteUserDTO extends ReadAccessTokenDTO {
  @NotBlank(message = "인증번호를 입력해주세요.")
  @Size(min = 8, max = 8, message = "대소문자, 숫자를 포함한 인증번호 8자리를 입력해주세요.")
  @JsonProperty("verify_string")
  private String verifyString;
}
