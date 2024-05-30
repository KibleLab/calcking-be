package kr.kro.calcking.calckingwebbe.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import kr.kro.calcking.calckingwebbe.entities.QuestionsAddEntity;

public interface QuestionsAddRepository
    extends MongoRepository<QuestionsAddEntity, String>, QuestionsAddRepositoryCustom {
  // CREATE
  public void createQuestionsAdd(String questionsID, String questionsTitle, String questionsLevel,
      List<Map<String, Object>> questions);

  // READ
  public Optional<QuestionsAddEntity> readQuestionsAddByQuestionsID(String questionsID);
}