package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.dtos.auth.DeleteUserDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAccessTokenDTO;
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

  @GetMapping
  public ResponseEntity<Map<String, Object>> getVerifyString(
      @Valid @RequestBody ReadAccessTokenDTO readAccessTokenDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.getVerifyString(readAccessTokenDTO, request, response);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> deleteUser(
      @Valid @RequestBody DeleteUserDTO deleteUserDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return deleteAccountService.deleteAccount(deleteUserDTO, request, response);
  }
}
