package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.documents.AuthCodeDocument;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.EmailProvider;
import kr.kro.calcking.calckingwebbe.providers.RandomStringProvider;
import kr.kro.calcking.calckingwebbe.providers.TokenProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import kr.kro.calcking.calckingwebbe.repositories.AuthCodeRepository;
import kr.kro.calcking.calckingwebbe.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteAccountService {
  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;
  private final AuthCodeRepository authCodeRepository;
  private final CookieProvider cookieProvider;
  private final TokenProvider tokenProvider;
  private final RandomStringProvider randomStringProvider;
  private final EmailProvider emailProvider;

  // POST (/delete-account/send)
  public ResponseEntity<Map<String, Object>> sendAuthCode(
      HttpServletRequest request, HttpServletResponse response) throws MessagingException {
    // AccessToken에서 uID 추출
    String authorizationHeader = request.getHeader("Authorization").substring(7);
    Optional<Map<String, Object>> payload = tokenProvider.getPayloadFromToken(authorizationHeader);
    String uID = (String) payload.get().get("ID");

    // 인증번호 생성
    String authCode = randomStringProvider.getRandomString(8, true, true);
    authCodeRepository.deleteAuthCodeByUID(uID);
    authCodeRepository.createAuthCodeByUID(authCode, uID);

    // 이메일 발송
    String email = userRepository.readUserByUID(uID).get().getUserEmailEntity().getUEmail();
    sendEmail(email, authCode);

    // 성공 응답
    return responseMap("인증번호 발송 성공!", HttpStatus.OK);
  }

  // POST (/delete-account/verify)
  public ResponseEntity<Map<String, Object>> verifyAuthCode(
      ReadAuthCodeDTO readAuthCodeDTO, HttpServletRequest request, HttpServletResponse response) {
    // AccessToken에서 uID 추출
    String authorizationHeader = request.getHeader("Authorization").substring(7);
    Optional<Map<String, Object>> payload = tokenProvider.getPayloadFromToken(authorizationHeader);
    String uID = (String) payload.get().get("ID");

    // 인증번호 검증 로직
    String authCode = readAuthCodeDTO.getAuthCode();
    Optional<AuthCodeDocument> authCodeDocument = authCodeRepository.readAuthCodeByUID(uID);
    if (authCodeDocument.isEmpty())
      return responseMap("인증번호가 만료되었습니다!", HttpStatus.BAD_REQUEST);
    if (!authCodeDocument.get().getAuthCode().equals(authCode))
      return responseMap("인증번호가 일치하지 않습니다!", HttpStatus.BAD_REQUEST);

    // 인증번호 인증 완료 토큰 발급
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("ID", uID);
    payloadMap.put("Verified", true);
    Date expireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 3));
    Optional<String> verifiedToken = tokenProvider.issueToken(payloadMap, expireAt);
    if (verifiedToken.isEmpty())
      return responseMap("토큰 생성 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
    else
      response.addHeader("Verified", verifiedToken.get().toString());

    // 성공 응답
    return responseMap("인증번호 검증 성공!", HttpStatus.OK);
  }

  // DELETE (/delete-account)
  public ResponseEntity<Map<String, Object>> deleteAccount(
      HttpServletRequest request, HttpServletResponse response) {
    // AccessToken에서 uID 추출
    String authorizationHeader = request.getHeader("Authorization").substring(7);
    Optional<Map<String, Object>> authorizationPayload = tokenProvider.getPayloadFromToken(authorizationHeader);
    String uID = (String) authorizationPayload.get().get("ID");

    // 인증번호 인증 완료 토큰 검증
    String verifiedHeader = request.getHeader("Verified");
    Optional<Map<String, Object>> verifiedPayload = tokenProvider.getPayloadFromToken(verifiedHeader);
    if (verifiedPayload.isEmpty())
      return responseMap("토큰이 만료되었습니다!", HttpStatus.BAD_REQUEST);
    else if (!verifiedPayload.get().get("ID").equals(uID))
      return responseMap("토큰이 일치하지 않습니다!", HttpStatus.BAD_REQUEST);

    // 회원탈퇴 로직
    Cookie refreshCookie = cookieProvider.getCookie("refresh_token", request);
    cookieProvider.deleteCookie(refreshCookie, response);
    tokenRepository.deleteTokenByUID(uID);
    userRepository.deleteUserByUID(uID);

    // 성공 응답
    return responseMap("회원탈퇴 성공!", HttpStatus.OK);
  }

  // 회원탈퇴 Email 발송 은닉 메서드
  private void sendEmail(String email, String authCode) throws MessagingException {
    String subject = "[연산군] 회원탈퇴 인증번호";
    String htmlContents = "<h2>[연산군] 회원탈퇴 인증번호</h2>"
        + "<h3>인증번호: " + authCode + "</h3>"
        + "<p>인증번호는 대소문자, 숫자를 구분하여 정확히 입력하시기 바랍니다.</p>"
        + "<p>인증번호는 3분 후 만료됩니다.</p>";
    emailProvider.sendHtmlEmail(email, subject, htmlContents);
  }

  // JSON 응답 생성 은닉 메서드
  private ResponseEntity<Map<String, Object>> responseMap(String message, HttpStatus status) {
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", message);
    responseMap.put("status", String.valueOf(status));
    return ResponseEntity.status(status).body(responseMap);
  }
}
