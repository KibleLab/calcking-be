package kr.kro.calcking.calckingwebbe.services.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.calcking.calckingwebbe.dtos.auth.SignUpDTO;
import kr.kro.calcking.calckingwebbe.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
  private final UserRepository userRepository;

  public ResponseEntity<?> signUp(SignUpDTO signUpDTO) {
    if (userRepository.findUserByID(signUpDTO.getUID()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 ID입니다!");
    }

    String uID = signUpDTO.getUID();
    String uPW = encodePW(signUpDTO.getUPW());
    String uName = signUpDTO.getUName();
    String uBirth = signUpDTO.getUBirth();
    String uPhone = signUpDTO.getUPhone();
    String uEmail = signUpDTO.getUEmail();

    userRepository.createUser(uID, uPW, uName, uBirth, uPhone, uEmail);

    // 휴대폰 인증 기능 추가 예정

    return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
  }

  private String encodePW(String rawPW) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(rawPW);
  }

}
