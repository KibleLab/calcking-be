package xyz.calcking.api.controllers.auth;

import xyz.calcking.api.services.auth.SignOutService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
