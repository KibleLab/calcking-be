package xyz.calcking.api.repositories;

import xyz.calcking.api.documents.QuestionsAddDocument;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QuestionsAddRepositoryCustom {
  // CREATE
  public void createQuestionsAdd(String questionsID, String questionsTitle, String questionsLevel,
      List<Map<String, Object>> questions);

  // READ
  public Optional<QuestionsAddDocument> readQuestionsAddByQuestionsID(String questionsID);
}
