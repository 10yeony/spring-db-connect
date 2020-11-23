package kr.com.inspect.report;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;

/**
 * 리스트를 메일로 전송하는 Class
 * @author Woo Young
 * @version 1.0
 *
 */

@Service
public class SendReport {
	/**
	 * 메일전송 기능을 위한 필드 선언
	 */
    @Autowired
    private JavaMailSender mailSender;

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

    /**
     * 파일을 받아서 sms 전송하는 메소드
     * @param file 파일
     * @param filename 파일 이름
     * @param phone 연락처 정보
     * @throws Exception 예외 처리
     */
    public void sendSMS(File file, String filename, String phone) throws Exception{
        /* sms 전송 관련 설정 api 키 입력 */
        String api_key = "NCSCZ2WQWBGNB44F";
        String api_secret = "LMEOPFADO6CVCQTTQ1AUEZVMCAO5HX97";

        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> set = new HashMap<String, String>();

        /* 문자 생성 */
        set.put("to", phone); // 수신번호
        set.put("from", "01062440346"); // 발신번호
        set.put("text", "sms 전송 테스트 문자입니다."); // 문자내용
        set.put("type", "sms"); // 문자 타입

        try {
            JSONObject obj = (JSONObject) coolsms.send(set);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
