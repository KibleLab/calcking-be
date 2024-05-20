package kr.kro.calcking.calckingwebbe.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.calcking.calckingwebbe.dtos.auth.CreateUserDTO;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
  private final UserRepository userRepository;

  // POST (/sign-up)
  public ResponseEntity<Map<String, Object>> signUp(CreateUserDTO createUserDTO) {
    Map<String, Object> responseMap = new HashMap<>();

    // User 중복 검증 로직
    if (userRepository.readUserByUID(createUserDTO.getUID()).isPresent()) {
      responseMap.put("message", "이미 존재하는 ID입니다!");
      responseMap.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
    }

    // 회원가입 로직
    String uID = createUserDTO.getUID();
    String uPW = encodePW(createUserDTO.getUPW());
    String uName = createUserDTO.getUName();
    String uBirth = createUserDTO.getUBirth();
    String uPhone = createUserDTO.getUPhone();
    String uEmail = createUserDTO.getUEmail();

    userRepository.createUser(uID, uPW, uName, uBirth, uPhone, uEmail);

    // 휴대폰 인증 로직 예정

    // JSON 응답 로직
    responseMap.put("message", "회원가입 성공!");
    responseMap.put("status", String.valueOf(HttpStatus.CREATED));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
  }

  // PW 암호화 은닉 메서드
  private String encodePW(String rawPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(rawPW);
  }
}
