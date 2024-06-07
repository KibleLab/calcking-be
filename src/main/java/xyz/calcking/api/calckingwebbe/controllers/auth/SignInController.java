package xyz.calcking.api.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.dtos.auth.ReadUserDTO;
import xyz.calcking.api.calckingwebbe.services.auth.SignInService;

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
