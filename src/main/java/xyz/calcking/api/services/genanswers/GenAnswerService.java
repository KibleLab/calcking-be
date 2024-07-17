package xyz.calcking.api.services.genanswers;

import xyz.calcking.api.documents.QuestionsAddDocument;
import xyz.calcking.api.dtos.genanswers.ReadAnswersDTO;
import xyz.calcking.api.repositories.QuestionsAddRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenAnswerService {
  private final QuestionsAddRepository questionsAddRepository;

  // GET (/gen-answers/add?questionsID=#)
  public Optional<QuestionsAddDocument> genAnswersAdd(ReadAnswersDTO readAnswersDTO) {
    return questionsAddRepository.readQuestionsAddByQuestionsID(readAnswersDTO.getQuestionsID());
  }
}
