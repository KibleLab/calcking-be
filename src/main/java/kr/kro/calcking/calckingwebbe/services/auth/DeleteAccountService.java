package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.calcking.calckingwebbe.dtos.auth.DeleteUserDTO;
import kr.kro.calcking.calckingwebbe.dtos.auth.ReadAccessTokenDTO;
import kr.kro.calcking.calckingwebbe.entities.UserVerifyStringEntity;
import kr.kro.calcking.calckingwebbe.providers.CookieProvider;
import kr.kro.calcking.calckingwebbe.providers.JWTProvider;
import kr.kro.calcking.calckingwebbe.providers.RandomStringProvider;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import kr.kro.calcking.calckingwebbe.repositories.UserTokenRepository;
import kr.kro.calcking.calckingwebbe.repositories.UserVerifyStringRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteAccountService {
  private final UserRepository userRepository;
  private final UserTokenRepository userTokenRepository;
  private final UserVerifyStringRepository userVerifyStringRepository;
  private final JWTProvider jwtProvider;
  private final CookieProvider cookieProvider;
  private final RandomStringProvider randomStringProvider;

  // POST (/delete-account)
  public ResponseEntity<Map<String, Object>> getVerifyString(
      ReadAccessTokenDTO readAccessTokenDTO,
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();

    // AccessToken 검증 로직
    String accessToken = readAccessTokenDTO.getAccessToken();
    String refreshToken = cookieProvider.getTokenFromCookie(cookieProvider.getCookie("refresh_token", request));
    Date accessTokenIssuedAt = jwtProvider.getIssuedAtFromAccessToken(accessToken);
    Date refreshTokenIssuedAt = jwtProvider.getIssuedAtFromRefreshToken(refreshToken);
    if (jwtProvider.isExpiredAccessToken(readAccessTokenDTO.getAccessToken())) {
      responseMap.put("message", "AccessToken이 만료되었습니다.");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    } else if (!accessTokenIssuedAt.equals(refreshTokenIssuedAt)) {
      responseMap.put("message", "AccessToken이 유효하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    }

    // 인증번호 생성 로직
    String uID = jwtProvider.getUIDFromAccessToken(readAccessTokenDTO.getAccessToken());
    String verifyString = randomStringProvider.getRandomString(8, true, true);
    Date expireAt = new Date(System.currentTimeMillis() + (1000 * 60 * 3));
    userVerifyStringRepository.deleteUserVerifyStringByUID(uID);
    userVerifyStringRepository.createUserVerifyString(uID, verifyString, expireAt);

    // JSON 응답 로직
    responseMap.put("verify_string", verifyString);
    responseMap.put("expire_at", expireAt);
    responseMap.put("message", "인증번호 발급 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // DELETE (/delete-account)
  public ResponseEntity<Map<String, Object>> deleteAccount(
      DeleteUserDTO deleteUserDTO,
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> responseMap = new HashMap<>();

    // AccessToken 검증 로직
    String accessToken = deleteUserDTO.getAccessToken();
    String refreshToken = cookieProvider.getTokenFromCookie(cookieProvider.getCookie("refresh_token", request));
    Date accessTokenIssuedAt = jwtProvider.getIssuedAtFromAccessToken(accessToken);
    Date refreshTokenIssuedAt = jwtProvider.getIssuedAtFromRefreshToken(refreshToken);
    if (jwtProvider.isExpiredAccessToken(deleteUserDTO.getAccessToken())) {
      responseMap.put("message", "AccessToken이 만료되었습니다.");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    } else if (!accessTokenIssuedAt.equals(refreshTokenIssuedAt)) {
      responseMap.put("message", "AccessToken이 유효하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
    }

    // 인증번호 검증 로직
    String uID = jwtProvider.getUIDFromAccessToken(deleteUserDTO.getAccessToken());
    Optional<UserVerifyStringEntity> userVerifyString = userVerifyStringRepository.readUserVerifyStringByUID(uID);
    if (userVerifyString.get().getUVerfyStringExpireAt().before(new Date())) {
      responseMap.put("message", "인증번호가 만료되었습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }
    if (!userVerifyString.get().getUVerifyString().equals(deleteUserDTO.getVerifyString())) {
      responseMap.put("message", "인증번호가 일치하지 않습니다!");
      responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

    // 회원탈퇴 로직
    userTokenRepository.deleteUserTokenByUID(uID);
    userVerifyStringRepository.deleteUserVerifyStringByUID(uID);
    userRepository.deleteUserByUID(uID);

    // JSON 응답 로직
    responseMap.put("message", "회원탈퇴 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.NO_CONTENT));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMap);
  }
}