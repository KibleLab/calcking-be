package xyz.calcking.api.calckingwebbe.controllers.genanswers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.documents.QuestionsAddDocument;
import xyz.calcking.api.calckingwebbe.dtos.genanswers.ReadAnswersDTO;
import xyz.calcking.api.calckingwebbe.services.genanswers.GenAnswerService;

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
