package xyz.calcking.api.calckingwebbe.documents;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "questions_add")
public class QuestionsAddDocument {
  @Id
  @Field(name = "questions_id")
  @JsonProperty("questions_id")
  private String questionsID;

  @Field(name = "questions_title")
  @JsonProperty("questions_title")
  private String questionsTitle;

  @Field(name = "questions_level")
  @JsonProperty("questions_level")
  private String questionsLevel;

  @Field(name = "questions")
  @JsonProperty("questions")
  private List<Map<String, Object>> questions;

  @Field(name = "expire_at")
  @JsonProperty("expire_at")
  private Date expireAt;
}
