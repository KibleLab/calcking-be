package kr.kro.calcking.calckingwebbe.repositories;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import kr.kro.calcking.calckingwebbe.documents.QuestionsAddDocument;

@Repository
public class QuestionsAddRepositoryCustomImpl implements QuestionsAddRepositoryCustom {
  @Autowired
  private MongoTemplate mongoTemplate;

  // CREATE
  @Override
  public void createQuestionsAdd(String questionsID, String questionsTitle, String questionsLevel,
      List<Map<String, Object>> questions) {
    QuestionsAddDocument questionsEntity = new QuestionsAddDocument();
    questionsEntity.setQuestionsID(questionsID);
    questionsEntity.setQuestionsTitle(questionsTitle);
    questionsEntity.setQuestionsLevel(questionsLevel);
    questionsEntity.setQuestions(questions);
    questionsEntity.setExpireAt(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 90));
    mongoTemplate.insert(questionsEntity);
  }

  // READ
  @Override
  public Optional<QuestionsAddDocument> readQuestionsAddByQuestionsID(String questionsID) {
    return Optional.ofNullable(
        mongoTemplate.findOne(Query.query(Criteria.where("questions_id").is(questionsID)), QuestionsAddDocument.class));
  }
}
