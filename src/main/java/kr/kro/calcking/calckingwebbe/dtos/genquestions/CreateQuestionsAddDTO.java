package kr.kro.calcking.calckingwebbe.dtos.genquestions;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateQuestionsAddDTO {
  @NotNull(message = "원하시는 문제 수를 입력해주세요.")
  @Min(value = 8, message = "문제 수는 8개 이상이어야 합니다.")
  @Max(value = 20, message = "문제 수는 20개 이하여야 합니다.")
  private int numberOfQuestions;

  @NotNull(message = "올림 횟수를 입력해주세요.")
  @Min(value = 0, message = "올림 횟수는 0 이상이어야 합니다.")
  @Max(value = 3, message = "올림 횟수는 3 이하여야 합니다.")
  private int carry;
}
