package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.dtos.auth.CreateUserDTO;
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

  @PostMapping
  public ResponseEntity<Map<String, Object>> signUp(@Valid @RequestBody CreateUserDTO createUserDTO) {
    return signUpService.signUp(createUserDTO);
  }
}
