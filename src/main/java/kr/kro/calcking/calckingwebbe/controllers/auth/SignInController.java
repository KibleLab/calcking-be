package kr.kro.calcking.calckingwebbe.controllers.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadUserDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignInService;

import java.util.Map;

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
  public ResponseEntity<Map<String, Object>> signIn(
      @Valid @RequestBody ReadUserDTO readUserDTO,
      HttpServletResponse response) {
    return signInService.signIn(readUserDTO, response);
  }
}
