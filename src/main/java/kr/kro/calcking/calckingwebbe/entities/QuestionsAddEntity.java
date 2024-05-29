package kr.kro.calcking.calckingwebbe.entities;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "questions_add")
public class QuestionsAddEntity {
  @Id
  @Column(name = "questions_id", length = 8)
  @JsonProperty("questions_id")
  private String questionsID;

  @Column(name = "questions_title", length = 20)
  @JsonProperty("questions_title")
  private String questionsTitle;

  @Column(name = "questions_level", length = 10)
  @JsonProperty("questions_level")
  private String questionsLevel;

  @Column(name = "questions")
  @JsonProperty("questions")
  private List<Map<String, Object>> questions;
}
