package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.annotations.ValidateAccessToken;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import kr.kro.calcking.calckingwebbe.services.auth.DeleteAccountService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delete-account")
public class DeleteAccountController {
  private final DeleteAccountService deleteAccountService;

  @ValidateAccessToken
  @PostMapping("/send")
  public ResponseEntity<Map<String, Object>> sendAuthCode(
      HttpServletRequest request, HttpServletResponse response) throws MessagingException {
    return deleteAccountService.sendAuthCode(request, response);
  }

  @ValidateAccessToken
  @PostMapping("/verify")
  public ResponseEntity<Map<String, Object>> verifyAuthCode(
      @Valid @RequestBody ReadAuthCodeDTO readAuthCodeDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.verifyAuthCode(readAuthCodeDTO, request, response);
  }

  @ValidateAccessToken
  @DeleteMapping
  public ResponseEntity<Map<String, Object>> deleteUser(
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.deleteAccount(request, response);
  }
}
