package xyz.calcking.api.controllers.auth;

import xyz.calcking.api.dtos.auth.ReadUserDTO;
import xyz.calcking.api.services.auth.SignInService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
