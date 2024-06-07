package xyz.calcking.api.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.annotations.ValidateAccessToken;
import xyz.calcking.api.calckingwebbe.dtos.auth.ReadAuthCodeDTO;
import xyz.calcking.api.calckingwebbe.services.auth.DeleteAccountService;

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
