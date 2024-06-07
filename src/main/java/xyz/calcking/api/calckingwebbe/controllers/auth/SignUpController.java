package xyz.calcking.api.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.dtos.auth.CreateUserDTO;
import xyz.calcking.api.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import xyz.calcking.api.calckingwebbe.dtos.auth.ReadEmailDTO;
import xyz.calcking.api.calckingwebbe.services.auth.SignUpService;

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
