package kr.kro.calcking.calckingwebbe.controllers.genanswers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.calcking.calckingwebbe.documents.QuestionsAddDocument;
import kr.kro.calcking.calckingwebbe.services.genanswers.GenAnswerService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gen-answers")
public class GenAnswersController {
  private final GenAnswerService genAnswerService;

  @GetMapping("/add")
  public Optional<QuestionsAddDocument> genAnswersAdd(
      @RequestParam(name = "q_id", required = true, defaultValue = "#") String questionsID) {
    System.out.println("questionsID: " + questionsID);
    return genAnswerService.genAnswersAdd(questionsID);
  }
}
