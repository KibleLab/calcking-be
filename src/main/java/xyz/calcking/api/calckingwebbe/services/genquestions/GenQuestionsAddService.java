package xyz.calcking.api.calckingwebbe.services.genquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.dtos.genquestions.CreateQuestionsAddDTO;
import xyz.calcking.api.calckingwebbe.providers.RandomStringProvider;
import xyz.calcking.api.calckingwebbe.repositories.QuestionsAddRepository;

@Service
@RequiredArgsConstructor
public class GenQuestionsAddService {
  private final static int CARRY_NONE = 0;
  private final static int CARRY_ONCE = 1;
  private final static int CARRY_TWICE = 2;
  private final static int CARRY_3TIMES = 3;
  private final QuestionsAddRepository questionsRepository;
  private final RandomStringProvider randomStringProvider;

  // GET (/gen-questions/add/units-to-units?numberOfQuestions=#&carry=#)
  // (몇 + 몇)
  public ResponseEntity<Map<String, Object>> addUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇 + 몇", carry, CARRY_ONCE);
    String questionsLevel = "A-1";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, lastNumber;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumber = random.nextInt(8) + 1;
          lastNumber = random.nextInt(9 - firstNumber) + 1;
          break;
        case CARRY_ONCE: // 받아올림 있음
          firstNumber = random.nextInt(9) + 1;
          lastNumber = random.nextInt(firstNumber) + (10 - firstNumber);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/units-to-units-to-units?numberOfQuestions=#&carry=#)
  // (몇 + 몇 + 몇)
  public ResponseEntity<Map<String, Object>> addUnits2Units2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇 + 몇 + 몇", carry, CARRY_ONCE);
    String questionsLevel = "A-1";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, secondNumber, lastNumber;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumber = random.nextInt(7) + 1;
          secondNumber = random.nextInt(8 - firstNumber) + 1;
          lastNumber = random.nextInt(9 - firstNumber - secondNumber) + 1;
          break;
        case CARRY_ONCE: // 받아올림 있음
          firstNumber = random.nextInt(9) + 1;
          secondNumber = random.nextInt(9) + 1;
          lastNumber = random.nextInt(10 - Math.max(1, 10 - (firstNumber + secondNumber)))
              + Math.max(1, 10 - (firstNumber + secondNumber));
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, secondNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/tens-to-units?numberOfQuestions=#&carry=#)
  // (몇십 + 몇)
  public ResponseEntity<Map<String, Object>> addTens2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇십 + 몇", carry, CARRY_NONE);
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();

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
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/tens-units-to-units?numberOfQuestions=#&carry=#)
  // (몇십몇 + 몇)
  public ResponseEntity<Map<String, Object>> addTensUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇십몇 + 몇", carry, CARRY_ONCE);
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberTens, firstNumberUnits, lastNumber, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 있음
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/tens-units-to-tens-units?numberOfQuestions=#&carry=#)
  // (몇십몇 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addTensUnits2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇십몇 + 몇십몇", carry, CARRY_TWICE);
    String questionsLevel = "A-2";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberTens, firstNumberUnits, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 1회
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_TWICE: // 받아올림 2회
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, 0, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/hunds-to-units?numberOfQuestions=#&carry=#)
  // (몇백 + 몇)
  public ResponseEntity<Map<String, Object>> addHunds2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백 + 몇", carry, CARRY_NONE);
    String questionsLevel = "A-3";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, lastNumber, lastNumberUnits;
      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, 0, 0);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/hunds-to-tens-units?numberOfQuestions=#&carry=#)
  // (몇백 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHunds2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백 + 몇십몇", carry, CARRY_NONE);
    String questionsLevel = "A-3";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, 0, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/hunds-tens-to-units?numberOfQuestions=#&carry=#)
  // (몇백몇십 + 몇)
  public ResponseEntity<Map<String, Object>> addHundsTens2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백몇십 + 몇", carry, CARRY_NONE);
    String questionsLevel = "A-4";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, lastNumber, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET (/gen-questions/add/hunds-tens-to-tens-units?numberOfQuestions=#&carry=#)
  // (몇백몇십 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTens2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백몇십 + 몇십몇", carry, CARRY_ONCE);
    String questionsLevel = "A-4";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 있음
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(firstNumberTens) + (10 - firstNumberTens);
          lastNumberUnits = random.nextInt(9) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, 0);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET
  // (/gen-questions/add/hunds-tens-units-to-units?numberOfQuestions=#&carry=#)
  // (몇백몇십몇 + 몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2Units(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백몇십몇 + 몇", carry, CARRY_ONCE);
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 있음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, 0, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET
  // (/gen-questions/add/hunds-tens-units-to-tens-units?numberOfQuestions=#&carry=#)
  // (몇백몇십몇 + 몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2TensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백몇십몇 + 몇십몇", carry, CARRY_TWICE);
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberTens, lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 1회
          firstNumberHunds = random.nextInt(9) + 1;
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_TWICE: // 받아올림 2회
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, 0, lastNumberTens, lastNumberUnits);
          break;
        default: // 그 외의 경우
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 로직
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // GET
  // (/gen-questions/add/hunds-tens-units-to-hunds-tens-units?numberOfQuestions=#&carry=#)
  // (몇백몇십몇 + 몇백몇십몇)
  public ResponseEntity<Map<String, Object>> addHundsTensUnits2HundsTensUnits(
      CreateQuestionsAddDTO createQuestionsAddDTO, HttpServletRequest request) {
    // 문제지 생성
    long seed = System.nanoTime();
    Random random = new Random(seed);
    int numberOfQuestions = createQuestionsAddDTO.getNumberOfQuestions();
    int carry = createQuestionsAddDTO.getCarry();
    String questionsID = randomStringProvider.getRandomString(8, true, true);
    String questionsTitle = questionsTitle("몇백몇십몇 + 몇백몇십몇", carry, CARRY_3TIMES);
    String questionsLevel = "A-5";
    List<Map<String, Object>> questions = new ArrayList<>();

    for (int i = 0; i < numberOfQuestions; i++) { // 문제수에 따라 문제 생성
      int firstNumber, firstNumberHunds, firstNumberTens, firstNumberUnits, lastNumber, lastNumberHunds, lastNumberTens,
          lastNumberUnits;

      switch (carry) {
        case CARRY_NONE: // 받아올림 없음
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(8) + 1;
          firstNumberUnits = random.nextInt(8) + 1;
          lastNumberHunds = random.nextInt(9 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(9 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(9 - firstNumberUnits) + 1;
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_ONCE: // 받아올림 1회
          firstNumberHunds = random.nextInt(8) + 1;
          firstNumberTens = random.nextInt(7) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberHunds = random.nextInt(9 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(8 - firstNumberTens) + 1;
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_TWICE: // 받아올림 2회
          firstNumberHunds = random.nextInt(7) + 1;
          firstNumberTens = random.nextInt(9) + 1;
          firstNumberUnits = random.nextInt(9) + 1;
          lastNumberHunds = random.nextInt(8 - firstNumberHunds) + 1;
          lastNumberTens = random.nextInt(10 - Math.max(1, 9 - firstNumberTens)) + Math.max(1, 9 - firstNumberTens);
          lastNumberUnits = random.nextInt(firstNumberUnits) + (10 - firstNumberUnits);
          firstNumber = composeNumber(0, firstNumberHunds, firstNumberTens, firstNumberUnits);
          lastNumber = composeNumber(0, lastNumberHunds, lastNumberTens, lastNumberUnits);
          break;
        case CARRY_3TIMES: // 받아올림 3회
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
          return responseMap("받아올림 횟수를 확인해주세요.", HttpStatus.BAD_REQUEST);
      }
      // 문제 리스트에 문제 누적
      questions.add(question(i + 1, firstNumber, lastNumber, firstNumber + lastNumber));
    }

    // 문제지를 DB에 저장
    questionsRepository.createQuestionsAdd(questionsID, questionsTitle, questionsLevel, questions);

    // 성공 응답
    return responseMap(questionsID, questionsTitle, questionsLevel, questions, "문제 생성 성공!", HttpStatus.CREATED);
  }

  // 숫자를 결합하는 은닉 메서드
  private static int composeNumber(int thous, int hunds, int tens, int units) {
    return thous * 1000 + hunds * 100 + tens * 10 + units;
  }

  // 받아올림 여부를 문제 제목에 결합하는 은닉 메서드
  private String questionsTitle(String questionsTitle, int carry, int carryMax) {
    String carryText = "";
    switch (carry) {
      case CARRY_NONE:
        carryText = (carryMax == CARRY_NONE) ? "" : " (받아올림 없음)";
        break;
      case CARRY_ONCE:
        carryText = (carryMax == CARRY_ONCE) ? " (받아올림 있음)" : " (받아올림 1회)";
        break;
      case CARRY_TWICE:
        carryText = " (받아올림 2회)";
        break;
      case CARRY_3TIMES:
        carryText = " (받아올림 3회)";
        break;
      default:
        break;
    }
    return questionsTitle + carryText;
  }

  // 문제 객체 생성 은닉 메서드 (두 수의 합)
  private Map<String, Object> question(int questionNumber, int firstNumber, int lastNumber, int answer) {
    Map<String, Object> question = new HashMap<>();
    question.put("question_number", questionNumber);
    question.put("first_number", firstNumber);
    question.put("last_number", lastNumber);
    question.put("operator", "+");
    question.put("answer", answer);
    return question;
  }

  // 문제 객체 생성 은닉 메서드 (세 수의 합)
  private Map<String, Object> question(
      int questionNumber, int firstNumber, int secondNumber, int lastNumber, int answer) {
    Map<String, Object> question = new HashMap<>();
    question.put("question_number", questionNumber);
    question.put("first_number", firstNumber);
    question.put("second_number", secondNumber);
    question.put("third_number", lastNumber);
    question.put("operator", "+");
    question.put("answer", answer);
    return question;
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }

  // JSON 응답 (문제지 포함) 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String questionsID, String questionsTitle,
      String questionsLevel, List<Map<String, Object>> questions, String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("questions_id", questionsID);
    responseMap.put("questions_title", questionsTitle);
    responseMap.put("questions_level", questionsLevel);
    responseMap.put("questions", questions);
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}