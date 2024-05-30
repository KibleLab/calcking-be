package kr.kro.calcking.calckingwebbe.controllers.genanswers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.documents.QuestionsAddDocument;
import kr.kro.calcking.calckingwebbe.dtos.genanswers.ReadAnswersDTO;
import kr.kro.calcking.calckingwebbe.services.genanswers.GenAnswerService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gen-answers")
public class GenAnswersController {
  private final GenAnswerService genAnswerService;

  @GetMapping("/add")
  public Optional<QuestionsAddDocument> genAnswersAdd(@Valid @ModelAttribute ReadAnswersDTO readAnswersDTO) {
    return genAnswerService.genAnswersAdd(readAnswersDTO);
  }
}
