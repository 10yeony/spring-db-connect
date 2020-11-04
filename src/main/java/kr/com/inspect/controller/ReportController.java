package kr.com.inspect.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.com.inspect.dto.Metadata;
import org.apache.lucene.util.packed.DirectMonotonicReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.Sound;
import kr.com.inspect.report.DocxReport;
import kr.com.inspect.report.HwpReport;
import kr.com.inspect.report.PptxReport;
import kr.com.inspect.report.XlsxReport;

@Controller
public class ReportController {
	/* PostgreSQL */
	@Autowired 
	private PostgreDao postgreDao;
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
	private String s = File.separator; //파일 구분
	
	@GetMapping("/reportPage")
	public String moveToReportPage() {
		return "report/reportPage";
	}
	
	@GetMapping("/report/{format}")
	public String writeReport(HttpServletRequest request, 
										Model model,
										@PathVariable String format) {
		list = postgreDao.getMetadataAndProgram();
		System.out.println(list.toArray());
		String root = request.getSession().getServletContext().getRealPath("/");
		String path = root + "reports" + s;
		String url = "";
		
		switch(format) {
			case ("hwp"): //한글 파일
				path += "hwp" + s;
				// hwpReport.writeHwp(path, list);
				url = "report/hwpReport";
				break;
			case ("docx"): //docx 파일
				path += "docx" + s;
				docxReport.writeDocx(path, list);
				url = "report/docxReport";
				break;
			case ("xlsx"): //xlsx 파일
				path += "xlsx" + s;
				xlsxReport.writeXlsx(path, list);
				url = "report/xlsxReport";
				break;
			case ("pptx"): //pptx 파일 
				path += "pptx" + s;
				// pptxReport.writePptx(path, list);
				url = "report/pptxReport";
				break;
			default:
				break;
		}
		return url;
	}
}
