package vs.order_service.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender emailSender;

  private final String senderEmail = "test@gmail.com";

  public void sendSimpleMessage(String toUserEmail, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(senderEmail);
    message.setTo(toUserEmail);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }

}
