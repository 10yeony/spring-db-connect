package kr.com.inspect.sender;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;

/**
 * 리스트를 메일로 전송하는 Class
 * @author WooYoung Lee
 * @version 1.0
 *
 */
@Service
@PropertySource(value = "classpath:properties/sender.properties")
public class SendReport {

	/**
	 * 메일전송 기능을 위한 필드 선언
	 */
    @Autowired
    private JavaMailSender mailSender;
    
    /**
     * 발신 이메일
     */
    @Value("${mail.username}") 
    private String mailUsername;
    
    /**
     * 파일을 받아서 mail 전송하는 메소드
     * @param file 파일
     * @param filename 파일 이름
     * @param email 이메일 정보
     * @throws Exception 예외처리
     */
    public void sendMail(File file, String filename, String email) throws Exception{
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 받는 사람
            messageHelper.setTo(email);
            // 보내는 사람
            messageHelper.setFrom(mailUsername);
            // 메일 제목 (생략 가능)
            messageHelper.setSubject("메일 전송 테스트");
            // 메일 본문
            messageHelper.setText("메일 전송 테스트 입니다.");

            // 파일 첨부
            FileSystemResource fsr = new FileSystemResource(file);
            messageHelper.addAttachment(filename, fsr);

            mailSender.send(message);

            file.delete();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
