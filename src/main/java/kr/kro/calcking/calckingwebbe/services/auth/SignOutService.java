package kr.kro.calcking.calckingwebbe.services.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.dtos.auth.SignOutDTO;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignOutService {
  private final UserRepository userRepository;
  private final CookieProvider cookieProvider;

  public ResponseEntity<?> signOut(
      SignOutDTO signOutDTO,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (!userRepository.findUserByID(signOutDTO.getUID()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 접근입니다!");
    }

    try {
      Cookie accessCookie = cookieProvider.getCookie("accessToken", request);
      Cookie refreshCookie = cookieProvider.getCookie("refreshToken", request);

      userRepository.deleteUserToken(signOutDTO.getUID());
      cookieProvider.deleteCookie(accessCookie, response);
      cookieProvider.deleteCookie(refreshCookie, response);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }

    return ResponseEntity.status(HttpStatus.OK).body("로그아웃 성공!");
  }
}
