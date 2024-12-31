package membership;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
	  // 이메일 전송 메서드
    public boolean sendAuthCode(String toEmail, String authCode) {
        // SMTP 서버 설정
        String host = "smtp.naver.com";
        String port = "587"; // TLS 포트
        String username = "bjioazz12@naver.com"; // SMTP 서버 사용자 이메일
        String password = "Z64XEBXPLWF7"; // SMTP 서버 사용자 비밀번호

        // SMTP 설정 속성(Properties)
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 사용 설정
        props.put("mail.smtp.ssl.trust", host);

        // 인증 정보 설정
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 이메일 메시지 생성
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // 발신자
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // 수신자
            message.setSubject("이메일 인증 코드"); // 이메일 제목
            message.setText("인증 코드는 다음과 같습니다: " + authCode); // 이메일 내용

            // 이메일 전송
            Transport.send(message);

            return true; // 이메일 발송 성공
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // 이메일 발송 실패
        }
    }
}