package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReadUserDTO {
  @NotBlank(message = "ID를 입력해주세요.")
  @Size(min = 4, max = 20, message = "ID는 4자 이상 20자 이하로 입력해주세요.")
  @JsonProperty("u_id")
  private String uID;

  @NotBlank(message = "패스워드를 입력해주세요.")
  @Size(min = 8, max = 20, message = "패스워드는 8자 이상 20자 이하로 입력해주세요.")
  @JsonProperty("u_pw")
  private String uPW;
}
