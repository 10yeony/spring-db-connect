package kr.com.inspect.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
	private List<Metadata>  list;
	
	/* 파일 생성 */
	@Autowired
	private HwpReport hwpReport;
	
	@Autowired
	private DocxReport docxReport;
	
	@Autowired
	private XlsxReport xlsxReport;
	
	@Autowired
	private PptxReport pptxReport;
	
	@Value("${report.docx.directory}")
	private String docxPath;
	
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/* 보고서 작성 */
	@GetMapping("/report/{format}")
	public String writeReport(HttpServletRequest request,
							  HttpServletResponse response,
										Model model,
										@PathVariable String format) {
		list = postgreService.getMetadataAndProgram();
		String url = "";
		
		switch(format) {
			case ("hwp"): //한글 파일
				// hwpReport.writeHwp(hwpPath, list);
				url = "report/hwpReport";
				break;
			case ("docx"): //docx 파일
				docxReport.writeDocx(response, docxPath, list);
				url = "report/docxReport";
				break;
			case ("xlsx"): //xlsx 파일
				xlsxReport.writeXlsx(response, xlsxPath, list);
				url = "report/xlsxReport";
				break;
			case ("pptx"): //pptx 파일 
				// pptxReport.writePptx(pptxPath, list);
				url = "report/pptxReport";
				break;
			default:
				break;
		}
		return url;
	}
}
