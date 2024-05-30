package kr.kro.calcking.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.services.auth.ReissueTokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reissue-token")
public class ReissueTokenController {
  private final ReissueTokenService reissueTokenService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
    return reissueTokenService.reissueToken(request, response);
  }
}
