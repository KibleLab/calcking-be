package kr.kro.calcking.calckingwebbe.services.genanswers;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.kro.calcking.calckingwebbe.documents.QuestionsAddDocument;
import kr.kro.calcking.calckingwebbe.repositories.QuestionsAddRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenAnswerService {
  private final QuestionsAddRepository questionsAddRepository;

  // GET (/gen-answers/add?q_id=#)
  public Optional<QuestionsAddDocument> genAnswersAdd(String questionsID) {
    return questionsAddRepository.readQuestionsAddByQuestionsID(questionsID);
  }
}
