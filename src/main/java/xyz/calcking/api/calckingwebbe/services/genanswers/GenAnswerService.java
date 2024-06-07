package xyz.calcking.api.calckingwebbe.services.genanswers;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.documents.QuestionsAddDocument;
import xyz.calcking.api.calckingwebbe.dtos.genanswers.ReadAnswersDTO;
import xyz.calcking.api.calckingwebbe.repositories.QuestionsAddRepository;

@Service
@RequiredArgsConstructor
public class GenAnswerService {
  private final QuestionsAddRepository questionsAddRepository;

  // GET (/gen-answers/add?questionsID=#)
  public Optional<QuestionsAddDocument> genAnswersAdd(ReadAnswersDTO readAnswersDTO) {
    return questionsAddRepository.readQuestionsAddByQuestionsID(readAnswersDTO.getQuestionsID());
  }
}
