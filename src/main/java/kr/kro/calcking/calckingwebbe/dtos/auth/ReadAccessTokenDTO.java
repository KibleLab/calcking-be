package kr.kro.calcking.calckingwebbe.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReadAccessTokenDTO {
  @NotBlank(message = "AccessToken이 필요합니다.")
  @JsonProperty("access_token")
  private String accessToken;
}
