package xyz.calcking.api.calckingwebbe.dtos.genanswers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReadAnswersDTO {
  @NotBlank(message = "문제지 코드를 입력해주세요.")
  @Size(min = 8, max = 8, message = "문제지 코드 8자를 대소문자 구분하여 입력해주세요.")
  private String questionsID;
}
