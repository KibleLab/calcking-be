package xyz.calcking.api.calckingwebbe.providers;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RandomStringProvider {

  // 랜덤 문자열 생성 메서드
  public String getRandomString(int length, boolean useLetters, boolean useNumbers) {
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numbers = "0123456789";
    String base = "";

    // 사용할 문자열 종류 결정
    if (useLetters) {
      base += letters;
    }
    if (useNumbers) {
      base += numbers;
    }

    // 랜덤 문자열 생성 로직
    StringBuilder randomString = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int randomIndex = (int) (Math.random() * base.length());
      randomString.append(base.charAt(randomIndex));
    }

    return randomString.toString();
  }
}
