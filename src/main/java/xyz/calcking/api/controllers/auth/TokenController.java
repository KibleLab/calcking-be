package xyz.calcking.api.controllers.auth;

import xyz.calcking.api.services.auth.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {
  private final TokenService tokenService;

  @GetMapping("/reissue-token")
  public ResponseEntity<Map<String, Object>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    return tokenService.reissueToken(request, response);
  }
}
