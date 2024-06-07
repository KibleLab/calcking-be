package xyz.calcking.api.calckingwebbe.controllers.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import xyz.calcking.api.calckingwebbe.services.auth.SignOutService;

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
