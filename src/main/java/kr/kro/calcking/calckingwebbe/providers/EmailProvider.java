package kr.kro.calcking.calckingwebbe.providers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {
  private final JavaMailSender javaMailSender;

  // Text 형식 이메일 발송 메서드
  public void sendTextEmail(String email, String subject, String contents) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(email);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(contents);
    javaMailSender.send(simpleMailMessage);
  }

  // HTML 형식 이메일 발송 메서드
  public void sendHtmlEmail(String email, String subject, String htmlContents) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    messageHelper.setTo(email);
    messageHelper.setSubject(subject);
    messageHelper.setText(htmlContents, true);
    javaMailSender.send(mimeMessage);
  }
}
