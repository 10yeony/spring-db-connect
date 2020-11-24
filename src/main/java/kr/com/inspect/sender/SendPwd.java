package kr.com.inspect.sender;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 임시 비밀번호를 메일로 발송함
 * @author Yeonhee Kim
 *
 */
@PropertySource(value = "classpath:properties/sender.properties")
public class SendPwd {
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
     *  비밀번호 변경을 위한 인증번호 발송 메일을 보냄
    * @param email 회원 이메일
    * @param key 인증번호
    * @throws Exception 예외
     */
    public void sendMail(String email, String pwd) throws Exception{
    	try{
    		MimeMessage message = mailSender.createMimeMessage();
    		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

    		messageHelper.setTo(email); // 받는 사람
    		messageHelper.setFrom(mailUsername); // 보내는 사람
    		messageHelper.setSubject("SDTM 임시 비밀번호 발송(비밀번호 변경 바랍니다.)"); // 메일 제목 (생략 가능)
    		
    		String msg = "";
    		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
    		msg += "<h3 style='color: blue;'>임시 비밀번호입니다.</h3>";
    		msg += "<div style='font-size: 130%'>";
    		msg += "로그인 페이지로 돌아가서 임시 비밀번호 <strong>" ;
    		msg += pwd + "</strong> 로 로그인해주세요.<br/>";
    		msg += "로그인 후 비밀번호를 변경해주시길 바랍니다.</div><br/>";
    		messageHelper.setText(msg); // 메일 본문
    		
    		/* 메일 발송 */
    		mailSender.send(message);
        }catch (Exception e){
            //e.printStackTrace();
        }
    }
}
