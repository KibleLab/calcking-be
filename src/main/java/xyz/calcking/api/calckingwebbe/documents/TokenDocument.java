package xyz.calcking.api.calckingwebbe.documents;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Document(collection = "token")
public class TokenDocument {
  @Field(name = "token")
  @JsonProperty("token")
  private String token;

  @Field(name = "expire_at")
  @JsonProperty("expire_at")
  private Date expireAt;

  @Field(name = "u_id")
  @JsonProperty("u_id")
  private String uID;
}
