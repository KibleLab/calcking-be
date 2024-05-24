package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.annotations.ValidateAccessToken;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAccessTokenDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignOutService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-out")
public class SignOutController {
  private final SignOutService signOutService;

  @ValidateAccessToken
  @PostMapping
  public ResponseEntity<Map<String, Object>> signOut(
      @Valid @RequestBody ReadAccessTokenDTO readAccessTokenDTO,
      HttpServletRequest request, HttpServletResponse response) {
    return signOutService.signOut(readAccessTokenDTO, request, response);
  }
}
