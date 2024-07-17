package xyz.calcking.api.repositories;

import xyz.calcking.api.documents.QuestionsAddDocument;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionsAddRepository
    extends MongoRepository<QuestionsAddDocument, String>, QuestionsAddRepositoryCustom {
  // CREATE
  public void createQuestionsAdd(String questionsID, String questionsTitle, String questionsLevel,
      List<Map<String, Object>> questions);

  // READ
  public Optional<QuestionsAddDocument> readQuestionsAddByQuestionsID(String questionsID);
}