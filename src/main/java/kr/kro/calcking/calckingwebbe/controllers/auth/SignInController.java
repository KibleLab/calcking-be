package kr.kro.calcking.calckingwebbe.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import kr.kro.calcking.calckingwebbe.dtos.auth.SignInDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignInService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class SignInController {
  private final SignInService signInService;

  @PostMapping
  public ResponseEntity<?> signIn(@RequestBody @Valid SignInDTO signInDTO, HttpServletResponse response)
      throws JwtException, Exception {
    try {
      return signInService.signIn(signInDTO, response);
    } catch (ValidationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
