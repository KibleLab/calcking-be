package xyz.calcking.api.controllers.genanswers;

import xyz.calcking.api.documents.QuestionsAddDocument;
import xyz.calcking.api.dtos.genanswers.ReadAnswersDTO;
import xyz.calcking.api.services.genanswers.GenAnswerService;

import jakarta.validation.Valid;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
