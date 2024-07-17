package xyz.calcking.api.controllers.auth;

import xyz.calcking.api.dtos.auth.CreateUserDTO;
import xyz.calcking.api.dtos.auth.ReadAuthCodeDTO;
import xyz.calcking.api.dtos.auth.ReadEmailDTO;
import xyz.calcking.api.services.auth.SignUpService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
      @Valid @RequestBody ReadAuthCodeDTO readAuthCodeDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return signUpService.verifyAuthCode(readAuthCodeDTO, request, response);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> signUp(
      @Valid @RequestBody CreateUserDTO createUserDTO, HttpServletRequest request) {
    return signUpService.signUp(createUserDTO, request);
  }
}
