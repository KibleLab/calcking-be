package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.documents.AuthCodeDocument;
import kr.kro.calcking.calckingwebbe.dtos.auth.CreateUserDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadEmailDTO;
import kr.kro.calcking.calckingwebbe.entities.UserEmailEntity;
import kr.kro.calcking.calckingwebbe.providers.EmailProvider;
import kr.kro.calcking.calckingwebbe.providers.RandomStringProvider;
import kr.kro.calcking.calckingwebbe.providers.TokenProvider;
import kr.kro.calcking.calckingwebbe.repositories.AuthCodeRepository;
import kr.kro.calcking.calckingwebbe.repositories.UserEmailRepository;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
  private final UserRepository userRepository;
  private final UserEmailRepository userEmailRepository;
  private final AuthCodeRepository authCodeRepository;
  private final TokenProvider tokenProvider;
  private final EmailProvider emailProvider;
  private final RandomStringProvider randomStringProvider;

  // GET (/sign-up/send)
  public ResponseEntity<Map<String, Object>> sendAuthCode(ReadEmailDTO readEmailDTO, HttpServletResponse response)
      throws MessagingException {
    // Email 인증 여부 검증
    Optional<UserEmailEntity> userEmail = userEmailRepository.readUserEmail(readEmailDTO.getEmail());
    if (userEmail.isPresent() && userEmail.get().isVerified())
      if (userRepository.readUserByUEmail(readEmailDTO.getEmail()).isPresent())
        return responseMap("이미 가입된 사용자 입니다!", HttpStatus.BAD_REQUEST);
    userEmailRepository.deleteUserEmail(readEmailDTO.getEmail());
    authCodeRepository.deleteAuthCodeByUEmail(readEmailDTO.getEmail());

    // 인증번호 생성
    String authCode = randomStringProvider.getRandomString(8, true, true);
    authCodeRepository.createAuthCodeByUEmail(authCode, readEmailDTO.getEmail());

    // Email 인증번호 발송
    sendEmail(readEmailDTO.getEmail(), authCode);

    // Email 토큰 발급
    Map<String, Object> payload = new HashMap<>();
    payload.put("Email", readEmailDTO.getEmail());
    Date expireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 3));
    Optional<String> emailToken = tokenProvider.issueToken(payload, expireAt);
    if (emailToken.isEmpty())
      return responseMap("토큰 발급 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else
      response.addHeader("Email", emailToken.get().toString());

    // Email 등록 (Email 인증 여부: false)
    userEmailRepository.createUserEmail(readEmailDTO.getEmail());

    // 성공 응답
    return responseMap("인증번호 발급 성공!", HttpStatus.OK);
  }

  // POST (/sign-up/verify)
  public ResponseEntity<Map<String, Object>> verifyAuthCode(ReadAuthCodeDTO readAuthCodeDTO,
      HttpServletRequest request) {
    // Email 토큰 검증
    Optional<Map<String, Object>> payload = tokenProvider.getPayloadFromToken(request.getHeader("Email"));
    if (payload.isEmpty())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.BAD_REQUEST);

    // Email 등록 및 인증 여부 검증
    String email = payload.get().get("Email").toString();
    Optional<UserEmailEntity> userEmail = userEmailRepository.readUserEmail(email);
    if (userEmail.isEmpty() || userEmail.get().isVerified())
      return responseMap("인증번호를 재발송 하세요!", HttpStatus.BAD_REQUEST);

    // 인증번호 검증
    String authCode = readAuthCodeDTO.getAuthCode();
    Optional<AuthCodeDocument> authCodeDocument = authCodeRepository.readAuthCodeByUEmail(email);
    if (authCodeDocument.isEmpty())
      return responseMap("인증번호가 만료되었습니다!", HttpStatus.BAD_REQUEST);
    else if (!authCodeDocument.get().getAuthCode().equals(authCode))
      return responseMap("인증번호가 일치하지 않습니다!", HttpStatus.BAD_REQUEST);

    // Email 인증 완료 (Email 인증 여부: true)
    userEmailRepository.updateUserEmail(email, true);

    // 성공 응답
    return responseMap("인증 성공!", HttpStatus.OK);
  }

  // POST (/sign-up)
  public ResponseEntity<Map<String, Object>> signUp(CreateUserDTO createUserDTO, HttpServletRequest request) {
    // Email 토큰 검증
    Optional<Map<String, Object>> payload = tokenProvider.getPayloadFromToken(request.getHeader("Email"));
    if (payload.isEmpty())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.BAD_REQUEST);
    else if (!payload.get().get("Email").equals(createUserDTO.getUEmail()))
      return responseMap("토큰이 일치하지 않습니다!", HttpStatus.BAD_REQUEST);

    // Email 등록 및 인증 여부 검증
    String email = payload.get().get("Email").toString();
    Optional<UserEmailEntity> userEmail = userEmailRepository.readUserEmail(email);
    if (userEmail.isEmpty() || !userEmail.get().isVerified())
      return responseMap("인증되지 않은 사용자 입니다!", HttpStatus.BAD_REQUEST);

    // User 중복 검증
    if (userRepository.readUserByUID(createUserDTO.getUID()).isPresent())
      return responseMap("이미 가입된 사용자 입니다!", HttpStatus.BAD_REQUEST);

    // User 등록
    String uID = createUserDTO.getUID();
    String uPW = encodePW(createUserDTO.getUPW());
    String uName = createUserDTO.getUName();
    String uBirth = createUserDTO.getUBirth();
    String uPhone = createUserDTO.getUPhone();
    String uEmail = createUserDTO.getUEmail();
    int uRoleID = createUserDTO.getURoleID();
    userRepository.createUser(uID, uPW, uName, uBirth, uPhone, uEmail, uRoleID);

    // 성공 응답
    return responseMap("회원가입 성공!", HttpStatus.CREATED);
  }

  // 회원가입 Email 발송 은닉 메서드
  private void sendEmail(String email, String authCode) throws MessagingException {
    String subject = "[연산군] 회원가입 인증번호";
    String htmlContents = "<h2>[연산군] 회원가입 인증번호</h2>"
        + "<h3>인증번호: " + authCode + "</h3>"
        + "<p>인증번호는 대소문자, 숫자를 구분하여 정확히 입력하시기 바랍니다.</p>"
        + "<p>인증번호는 3분 후 만료됩니다.</p>";
    emailProvider.sendHtmlEmail(email, subject, htmlContents);
  }

  // PW 암호화 은닉 메서드
  private String encodePW(String rawPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(rawPW);
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}
