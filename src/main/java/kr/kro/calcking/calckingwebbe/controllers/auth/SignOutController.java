package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.services.auth.SignOutService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-out")
public class SignOutController {
  private final SignOutService signOutService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> signOut(
      HttpServletRequest request, HttpServletResponse response) {
    return signOutService.signOut(request, response);
  }
}
