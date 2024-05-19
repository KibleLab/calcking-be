package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
  @NotBlank(message = "ID를 입력해주세요.")
  @Size(min = 4, max = 20, message = "ID는 4자 이상 20자 이하로 입력해주세요.")
  @JsonProperty("u_id")
  private String uID;

  @NotBlank(message = "패스워드를 입력해주세요.")
  @Size(min = 8, max = 20, message = "패스워드는 8자 이상 20자 이하로 입력해주세요.")
  @JsonProperty("u_pw")
  private String uPW;

  @NotBlank(message = "이름을 입력해주세요.")
  @JsonProperty("u_name")
  private String uName;

  @NotBlank(message = "생년월일을 입력해주세요.")
  @Size(min = 8, max = 8, message = "생년월일은 8자로 입력해주세요.")
  @JsonProperty("u_birth")
  private String uBirth;

  @NotBlank(message = "전화번호를 입력해주세요.")
  @Size(min = 11, max = 11, message = "전화번호는 - 없이 입력해주세요.")
  @JsonProperty("u_phone")
  private String uPhone;

  @Email(message = "올바르지 않은 이메일 형식입니다.")
  @NotBlank(message = "이메일을 입력해주세요.")
  @JsonProperty("u_email")
  private String uEmail;
}
