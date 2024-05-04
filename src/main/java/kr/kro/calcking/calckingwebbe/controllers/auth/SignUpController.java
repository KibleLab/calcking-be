package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import kr.kro.calcking.calckingwebbe.dtos.auth.SignUpDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignUpService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-up")
public class SignUpController {
  private final SignUpService signUpService;

  @PostMapping
  public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO signUpDTO) throws ValidationException {
    try {
      return signUpService.signUp(signUpDTO);
    } catch (ValidationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

}
