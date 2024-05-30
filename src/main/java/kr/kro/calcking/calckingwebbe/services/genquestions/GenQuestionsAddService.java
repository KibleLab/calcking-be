package kr.kro.calcking.calckingwebbe.services.genquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kr.kro.calcking.calckingwebbe.dtos.genquestions.add.CreateQuestionsAddDTO;
import kr.kro.calcking.calckingwebbe.providers.RandomStringProvider;
import kr.kro.calcking.calckingwebbe.repositories.QuestionsAddRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenQuestionsAddService {
  private final QuestionsAddRepository questionsRepository;
  private final RandomStringProvider randomStringProvider;

  // 숫자를 결합하는 은닉 메서드
  private static int composeNumber(int thous, int hunds, int tens, int units) {
    return thous * 1000 + hunds * 100 + tens * 10 + units;
  }

  // GET (/gen-questions/add/units-to-units) (몇 + 몇)
  public ResponseEntity<Map<String, Object>> addUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇 + 몇 ";
    String questionsLevel = "A-1";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 있음)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, lastNumber;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumber = random.nextInt(8) + 1;
          lastNumber = random.nextInt(9 - firstNumber) + 1;
          break;
        case 1: // 받아올림 있음
          firstNumber = random.nextInt(9) + 1;
          lastNumber = random.nextInt(firstNumber) + (10 - firstNumber);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/units-to-units-to-units) (몇 + 몇 + 몇)
  public ResponseEntity<Map<String, Object>> addUnits2Units2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇 + 몇 + 몇 ";
    String questionsLevel = "A-1";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 있음)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, secondNumber, lastNumber;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumber = random.nextInt(7) + 1;
          secondNumber = random.nextInt(8 - firstNumber) + 1;
          lastNumber = random.nextInt(9 - firstNumber - secondNumber) + 1;
          break;
        case 1: // 받아올림 있음
          firstNumber = random.nextInt(9) + 1;
          secondNumber = random.nextInt(9) + 1;
          lastNumber = random.nextInt(10 - Math.max(1, 10 - (firstNumber + secondNumber)))
              + Math.max(1, 10 - (firstNumber + secondNumber));
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("second_number", secondNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + secondNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/tens-to-units) (몇십 + 몇)
  public ResponseEntity<Map<String, Object>> addTens2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇십 + 몇 ";
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberTens, lastNumber, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberTens = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, 0, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/tens-units-to-units) (몇십몇 + 몇)
  public ResponseEntity<Map<String, Object>> addTensUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇십몇 + 몇 ";
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 있음)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberTens, firstNumberUnits, lastNumber, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        case 1: // 받아올림 있음
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/tens-units-to-tens-units) (몇십몇 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addTensUnits2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇십몇 + 몇십몇 ";
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 1회)";
        break;
      case 2:
        questionsTitle += "(받아올림 2회)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberTens, firstNumberUnits, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case 1: // 받아올림 1회
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case 2: // 받아올림 2회
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-to-units) (몇백 + 몇)
  public ResponseEntity<Map<String, Object>> addHunds2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백 + 몇 ";
    String questionsLevel = "A-3";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, lastNumber, lastNumberUnits;
      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, 0, 0);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-to-tens-units) (몇백 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHunds2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백 + 몇십몇 ";
    String questionsLevel = "A-3";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, 0, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-tens-to-units) (몇백몇십 + 몇)
  public ResponseEntity<Map<String, Object>> addHundsTens2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백몇십 + 몇 ";
    String questionsLevel = "A-4";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, lastNumber, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-tens-to-tens-units) (몇백몇십 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTens2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백몇십 + 몇십몇 ";
    String questionsLevel = "A-4";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 있음)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case 1: // 받아올림 있음
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(firstNumberTens) + (10 - firstNumberTens);
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-tens-units-to-units) (몇백몇십몇 + 몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백몇십몇 + 몇 ";
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 있음)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        case 1: // 받아올림 있음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-tens-units-to-tens-units) (몇백몇십몇 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백몇십몇 + 몇십몇 ";
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 1회)";
        break;
      case 2:
        questionsTitle += "(받아올림 2회)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case 1: // 받아올림 1회
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case 2: // 받아올림 2회
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // GET (/gen-questions/add/hunds-tens-units-to-hunds-tens-units) (몇백몇십몇 + 몇백몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2HundsTensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    Map<String, Object> responseMap = new HashMap<>();

    // 문제 생성 로직
    long seed = System.nanoTime();
    Random random = new Random(seed);
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = "몇백몇십몇 + 몇백몇십몇 ";
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();

    switch (carry) { // 문제지 타이틀에 받아올림 여부 표시
      case 0:
        questionsTitle += "(받아올림 없음)";
        break;
      case 1:
        questionsTitle += "(받아올림 1회)";
        break;
      case 2:
        questionsTitle += "(받아올림 2회)";
        break;
      case 3:
        questionsTitle += "(받아올림 3회)";
        break;
    }

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberHunds, lastNumberTens,
          lastNumberUnits;

      switch (carry) {
        case 0: // 받아올림 없음
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberHunds = random.nextInt(9 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case 1: // 받아올림 1회
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberHunds = random.nextInt(9 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case 2: // 받아올림 2회
          firstNumberHunds = random.nextInt(7) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberHunds = random.nextInt(8 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case 3: // 받아올림 3회
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberHunds = random.nextInt(10 - Math.max(1, 9 - firstNumberHunds)) + Math.max(1, 9 - firstNumberHunds);
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          responseMap.put("message", "받아올림 횟수를 확인해주세요.");
          responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
      }

      Map<String, Object> question = new HashMap<>();
      question.put("question_number", i + 1);
      question.put("first_number", firstNumber);
      question.put("last_number", lastNumber);
      question.put("operator", "+");
      question.put("answer", firstNumber + lastNumber);
      questions.add(question);
    }

    // DB 저장 로직
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // JSON 응답 로직
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", "문제 생성 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }
}