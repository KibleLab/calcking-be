package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.dtos.auth.CreateUserDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadEmailDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignUpService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-up")
public class SignUpController {
  private final SignUpService signUpService;

  @PostMapping("/send")
  public ResponseEntity<Map<String, Object>> sendAuthCode(
      @Valid @RequestBody ReadEmailDTO readEmailDTO, HttpServletResponse response)
      throws MessagingException {
    return signUpService.sendAuthCode(readEmailDTO, response);
  }

  @PostMapping("/verify")
  public ResponseEntity<Map<String, Object>> verifyAuthCode(
      @Valid @RequestBody ReadAuthCodeDTO readAuthCodeDTO, HttpServletRequest request) {
    return signUpService.verifyAuthCode(readAuthCodeDTO, request);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> signUp(
      @Valid @RequestBody CreateUserDTO createUserDTO, HttpServletRequest request) {
    return signUpService.signUp(createUserDTO, request);
  }
}
