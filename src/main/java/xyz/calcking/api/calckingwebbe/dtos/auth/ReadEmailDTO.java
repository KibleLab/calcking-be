package xyz.calcking.api.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReadEmailDTO {
  @Email(message = "올바르지 않은 이메일 형식입니다.")
  @NotBlank(message = "이메일을 입력해주세요.")
  @JsonProperty("email")
  private String email;
}
