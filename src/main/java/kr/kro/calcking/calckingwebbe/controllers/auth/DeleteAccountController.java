package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.annotations.ValidateAccessToken;
import kr.kro.calcking.calckingwebbe.dtos.auth.DeleteUserDTO;
import kr.kro.calcking.calckingwebbe.services.auth.DeleteAccountService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delete-account")
public class DeleteAccountController {
  private final DeleteAccountService deleteAccountService;

  @ValidateAccessToken
  @GetMapping
  public ResponseEntity<Map<String, Object>> getVerifyString(
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.getVerifyString(request, response);
  }

  @ValidateAccessToken
  @PostMapping
  public ResponseEntity<Map<String, Object>> deleteUser(
      @Valid @RequestBody DeleteUserDTO deleteUserDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.deleteAccount(deleteUserDTO, request, response);
  }
}
