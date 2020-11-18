package kr.com.inspect.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.com.inspect.dto.Utterance;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.report.DocxReport;
import kr.com.inspect.report.HwpReport;
import kr.com.inspect.report.PptxReport;
import kr.com.inspect.report.XlsxReport;
import kr.com.inspect.service.PostgreService;

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class ReportController {
	/* PostgreSQL */
	@Autowired 
	private PostgreService postgreService;
	private List<Metadata>  metadata;
	private List<Utterance>  utterances;
	private Metadata meta;

	/* 파일 생성 */
	@Autowired
	private HwpReport hwpReport;
	
	@Autowired
	private DocxReport docxReport;
	
	@Autowired
	private XlsxReport xlsxReport;
	
	@Autowired
	private PptxReport pptxReport;

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${report.docx.directory}")
	private String docxPath;
	
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/* 한국어 강의 목록 리스트 파일로 출력 */
	@GetMapping("/metadata/{format}")
	public void writeMetadata(HttpServletResponse response,
										@PathVariable String format)throws Exception {
		metadata = postgreService.getMetadataAndProgram();

		switch(format) {
			case ("hwp"): //한글 파일
				// hwpReport.writeHwp(hwpPath, list);
				break;
			case ("docx"): //docx 파일
				try{
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
					messageHelper.setText("메일 테스트입니다.");
					messageHelper.setTo("dldndud61@naver.com");
					messageHelper.setSubject("메일 테스트");
					messageHelper.setFrom("wooyoung.lee@namutech.co.kr");

					mailSender.send(message);
				}catch (Exception e) {
					System.out.println(e);
				}
				docxReport.writeDocxMetadata(response, docxPath, metadata, "download");
				break;
			case ("xlsx"): //xlsx 파일
				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata);
				break;
			case ("pptx"): //pptx 파일 
				// pptxReport.writePptx(pptxPath, list);
				break;
			default:
				break;
		}
	}

	/* utterance 리스트 docx 파일로 출력 */
	@GetMapping("/utterance/docx/{format}")
	public void writeUtteranceDocx(HttpServletResponse response,
							  @PathVariable Integer format) {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		docxReport.writeDocxUtterance(response, docxPath, utterances, meta);
	}

	/* utterance 리스트 xlsx 파일로 출력 */
	@GetMapping("/utterance/xlsx/{format}")
	public void writeUtteranceXlsx(HttpServletRequest request,
							   HttpServletResponse response,
							   Model model,
							   @PathVariable Integer format) {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		xlsxReport.writeXlsxUtterance(response, xlsxPath, utterances, meta);
	}

	/* 한국어 강의 목록 mail 전송 */
	@GetMapping("/metadataMail/{format}")
	public void writeMetadataMail(HttpServletResponse response,
							  Model model,
							  @PathVariable String format)throws Exception {
		metadata = postgreService.getMetadataAndProgram();

		System.out.println("start Mail send");

		switch(format) {
			case ("docx"): //docx 파일
				try{
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
					messageHelper.setText("메일 테스트입니다.");
					messageHelper.setTo("dldndud61@naver.com");
					messageHelper.setSubject("메일 테스트");
					messageHelper.setFrom("wooyoung.lee@namutech.co.kr");

					mailSender.send(message);
				}catch (Exception e) {
					System.out.println(e);
				}
				break;
			case ("xlsx"): //xlsx 파일
				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata);
				break;
			default:
				break;
		}
	}
}
