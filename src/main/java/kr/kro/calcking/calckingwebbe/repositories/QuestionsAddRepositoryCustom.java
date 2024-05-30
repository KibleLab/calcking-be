package kr.kro.calcking.calckingwebbe.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import kr.kro.calcking.calckingwebbe.entities.QuestionsAddEntity;

public interface QuestionsAddRepositoryCustom {
  // CREATE
  public void createQuestionsAdd(String questionsID, String questionsTitle, String questionsLevel,
      List<Map<String, Object>> questions);

  // READ
  public Optional<QuestionsAddEntity> readQuestionsAddByQuestionsID(String questionsID);
}
