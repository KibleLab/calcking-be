package xyz.calcking.api.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ReadAuthCodeDTO {
  @NotBlank(message = "인증번호를 입력해주세요.")
  @Size(min = 8, max = 8, message = "대소문자, 숫자를 포함한 인증번호 8자리를 입력해주세요.")
  @JsonProperty("auth_code")
  private String authCode;
}
