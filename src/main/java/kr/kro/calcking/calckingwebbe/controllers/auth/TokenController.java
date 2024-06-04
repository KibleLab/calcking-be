package kr.kro.calcking.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.services.auth.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController {
  private final TokenService tokenService;

  @GetMapping("/reissue-token")
  public ResponseEntity<Map<String, Object>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    return tokenService.reissueToken(request, response);
  }
}
