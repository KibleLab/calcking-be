package kr.kro.calcking.calckingwebbe.controllers.auth;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import kr.kro.calcking.calckingwebbe.dtos.auth.SignOutDTO;
import kr.kro.calcking.calckingwebbe.services.auth.SignOutService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-out")
public class SignOutController {
  private final SignOutService signOutService;

  @PostMapping
  public ResponseEntity<?> signOut(
      @Valid @RequestBody SignOutDTO signOutDTO,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      return signOutService.signOut(signOutDTO, request, response);
    } catch (ValidationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

}
