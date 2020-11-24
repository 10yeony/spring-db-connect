package kr.com.inspect.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.report.SendReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.report.DocxReport;
import kr.com.inspect.report.XlsxReport;
import kr.com.inspect.service.PostgreService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Controller
@PropertySource(value = "classpath:properties/directory.properties")
public class ReportController {
	/* sms 전송확인을 위해 임시로 선언 */
	@Autowired
	SendReport sendReport;
	
	/**
	 * PostgreSQL
	 */
	/* PostgreSQL */
	@Autowired
	private PostgreService postgreService;
	private List<Metadata>  metadata;
	private List<Utterance>  utterances;
	private Metadata meta;

	/**
	 * 파일 생성
	 */
	@Autowired
	private DocxReport docxReport;
	
	/**
	 * 
	 */
	@Autowired
	private XlsxReport xlsxReport;

	@Value("${report.docx.directory}")
	private String docxPath;
	
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/**
	 * 한국어 강의 목록 리스트 파일로 출력
	 * @param response
	 * @param format
	 * @throws Exception
	 */
	@GetMapping("/metadata/{format}")
	public void writeMetadata(HttpServletResponse response,
							  @PathVariable String format) throws Exception {
		metadata = postgreService.getMetadataAndProgram();

		switch(format) {
			case ("hwp"): //한글 파일
				// hwpReport.writeHwp(hwpPath, list);
				break;
			case ("docx"): //docx 파일
				docxReport.writeDocxMetadata(response, docxPath, metadata, "download");
				System.out.println();
				break;
			case ("xlsx"): //xlsx 파일
				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata, "download");
				break;
			case ("pptx"): //pptx 파일 
				// pptxReport.writePptx(pptxPath, list);
				break;
			default:
				break;
		}
	}

	/**
	 * utterance 리스트 docx 파일로 출력
	 * @param response
	 * @param format
	 * @throws Exception
	 */
	@GetMapping("/utterance/docx/{format}")
	public void writeUtteranceDocx(HttpServletResponse response,
							  @PathVariable Integer format)throws Exception {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		docxReport.writeDocxUtterance(response, docxPath, utterances, meta, "download");
	}

	/**
	 * utterance 리스트 xlsx 파일로 출력
	 * @param response
	 * @param model
	 * @param format
	 * @throws Exception
	 */
	@GetMapping("/utterance/xlsx/{format}")
	public void writeUtteranceXlsx(HttpServletResponse response,
							   Model model,
							   @PathVariable Integer format)throws Exception {
		meta = postgreService.getMetadataAndProgramUsingId(format);
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		xlsxReport.writeXlsxUtterance(response, xlsxPath, utterances, meta, "download");
	}

	/**
	 * 한국어 강의목록 파일 mail 전송
	 * @param session
	 * @param response
	 * @param format
	 * @throws Exception
	 */
	@GetMapping("/metadataMail/{format}")
	public void sendMetadataMail(HttpSession session,
								 HttpServletResponse response,
							  @PathVariable String format) throws Exception {
		// 사용자의 이메일 정보를 받아옴
		Member member = (Member)session.getAttribute("member");
		String email = member.getEmail();
		// 파일에 출력할 metadata table
		metadata = postgreService.getMetadataAndProgram();
		switch(format) {
			case ("docx"): //docx 파일 , mail이라는 표시와 email정보를 함께 보냄
				docxReport.writeDocxMetadata(response, docxPath, metadata, "mail"+email);
				break;
			case ("xlsx"): //xlsx 파일, mail이라는 표시와 email정보를 함께 보냄
				System.out.println("xlsx파일 mail");
				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata, "mail"+email);
				break;
			default:
				break;
		}
	}

	/**
	 * 강의 문장 파일 mail 전송
	 * @param session
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/utteranceMail")
	@ResponseBody
	public void sendUtteranceMail(HttpSession session,
								  HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		// 사용자의 이메일 정보를 받아옴
		Member member = (Member)session.getAttribute("member");
		String email = member.getEmail();
		int format = Integer.parseInt(request.getParameter("metaId"));
		// 해당 utterance table의 metadata
		meta = postgreService.getMetadataAndProgramUsingId(format);
		// 파일에 출력할 utterance table
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		switch(request.getParameter("file")) {
			case ("docx"): //docx 파일
				System.out.println("docx");
				docxReport.writeDocxUtterance(response, docxPath, utterances, meta, "mail"+email);
				break;
			case ("xlsx"): //xlsx 파일
				System.out.println("xlsxl");
				xlsxReport.writeXlsxUtterance(response, xlsxPath, utterances, meta, "mail"+email);
				break;
			default:
				break;
		}
	}

	/* 한국어 강의 목록 파일 sms 전송 */
	@GetMapping("/metadataSMS/{format}")
	public void sendMetadataSMS(HttpSession session,
								 HttpServletResponse response,
								 @PathVariable String format) throws Exception {
		// 사용자의 phone 정보를 받아옴
		Member member = (Member)session.getAttribute("member");
		String phone = member.getPhone();
		// 파일에 출력할 metadata table
		metadata = postgreService.getMetadataAndProgram();

		switch(format) {
			case ("docx"): //docx 파일 , sms이라는 표시와 phone정보를 함께 보냄
				System.out.println(phone+"docx");
				sendReport.sendSMS(null, null, phone);
//				docxReport.writeDocxMetadata(response, docxPath, metadata, "sms"+phone);
				break;
			case ("xlsx"): //xlsx 파일, sms이라는 표시와 phone정보를 함께 보냄
				System.out.println(phone);
//				xlsxReport.writeXlsxMetadata(response, xlsxPath, metadata, "sms"+phone);
				break;
			default:
				break;
		}
	}

	/* 강의 문장 파일 sms 전송 */
	@GetMapping("/utteranceSMS")
	@ResponseBody
	public void sendUtteranceSMS(HttpSession session,
								  HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		// 사용자의 phone 정보를 받아옴
		Member member = (Member)session.getAttribute("member");
		String phone = member.getPhone();
		int format = Integer.parseInt(request.getParameter("metaId"));
		// 해당 utterance table의 metadata
		meta = postgreService.getMetadataAndProgramUsingId(format);
		// 파일에 출력할 utterance table
		utterances = postgreService.getUtteranceUsingMetadataId(format);

		switch(request.getParameter("file")) {
			case ("docx"): //docx 파일
				System.out.println(phone + "docx");
//				sendReport.sendSMS(null, null, phone);
//				docxReport.writeDocxUtterance(response, docxPath, utterances, meta, "sms"+phone);
				break;
			case ("xlsx"): //xlsx 파일
				System.out.println(phone + "xlsxl");
//				xlsxReport.writeXlsxUtterance(response, xlsxPath, utterances, meta, "sms"+phone);
				break;
			default:
				break;
		}
	}
}
