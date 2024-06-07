package xyz.calcking.api.calckingwebbe.documents;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@Document(collection = "auth_code")
public class AuthCodeDocument {
  @Field(name = "auth_code")
  @JsonProperty("auth_code")
  private String authCode;

  @Field(name = "expire_at")
  @JsonProperty("expire_at")
  private Date expireAt;

  @Field(name = "u_id")
  @JsonProperty("u_id")
  private String uID;

  @Field(name = "u_email")
  @JsonProperty("u_email")
  private String uEmail;
}
