package kr.com.inspect.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 
 * @author Woo Young
 * @version 1.0
 *
 */

@Service
public class MailSend {
	/**
	 * 
	 */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 파일을 받아서 mail 전송하는 메소드
     * @param file
     * @param filename
     * @param email
     * @throws Exception
     */
    public void sendMail(File file, String filename, String email) throws Exception{
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            // 받는 사람
            messageHelper.setTo(email);
            // 보내는 사람
            messageHelper.setFrom("wooyoung.lee@namutech.co.kr");
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
