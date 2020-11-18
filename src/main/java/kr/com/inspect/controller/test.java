package kr.com.inspect.controller;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

public class test {

    public static void main(String[] args) throws Exception {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("wooyoung.lee@namutech.co.kr");
        mailSender.setPassword("keabthweiiovgczx");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);

        /* File 생성 */
        String filename = "test.docx";
        String path = "/opt/tomcat/resources/report/docx/";
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();

        r.setText("테스트용 문서입니다.");
        r.setFontSize(9);
        r.addBreak();r.addBreak();

        File file = new File(path + filename);
        FileOutputStream fos = null;
        fos = new FileOutputStream(file);
        doc.write(fos);

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setText("메일 테스트입니다.");
            messageHelper.setTo("dldndud61@naver.com");
            messageHelper.setSubject("메일 테스트");
            messageHelper.setFrom("wooyoung.lee@namutech.co.kr");

            messageHelper.addAttachment(filename, file);

            mailSender.send(message);
            file.delete();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
