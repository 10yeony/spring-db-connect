package kr.com.inspect.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.sender.SendReport;
import kr.com.inspect.util.ClientInfo;

/**
 * docx 타입으로 리스트 파일 작성
 * @author Woo Young
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Service
@PropertySource(value = "classpath:properties/report.properties")
public class DocxReport {
	
	/**
	 * 메일과 sms 전송을 위한 SendReport 필드 선언
	 */
	@Autowired
	private SendReport ms;

	/**
	 * 사용자 접속 정보를 가져오는 객체
	 */
	@Autowired
	private ClientInfo clientInfo;

	/**
	 * metadata의 id 컬럼
	 */
	@Value("${table.column0}")
	private String column0;

	/**
	 * metadata의 creator 컬럼
	 */
	@Value("${table.column1}")
	private String column1;

	/**
	 * docx 한국어 강의 목록 리스트 작성
	 * @param response 사용자에게 보내는 응답
	 * @param path 파일 디렉토리
	 * @param list 객체를 담을 리스트
	 * @param flag 해당 요청이 download인지, mail인지, sms인지 결정해주는 변수
	 * @param title 파일 내용의 제목
	 * @throws Exception 예외처리
	 */
	public void writeDocxMetadata(HttpServletResponse response, 
										String path, 
										List<Metadata> list, 
										String flag,
										String title)throws Exception {
		String docxFileName =
				"LectureList_"+
				new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".docx";
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		/* doc 파일 생성 */
		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();

		r.setText("날짜 : " + day);
		r.setFontSize(10);
		r.addBreak();r.addBreak();

		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setText(title);
		r1.setBold(true);
		r1.setFontSize(17);
		r1.addBreak();
		r1.addBreak();


		XWPFTable table = doc.createTable(list.size()+1, 8);

		/* 헤더 정보 구성 */
		table.getRow(0).getCell(0).setWidth("500");
		table.getRow(0).getCell(0).setText(column0);
		table.getRow(0).getCell(1).setWidth("2000");
		table.getRow(0).getCell(1).setText("제목");
		table.getRow(0).getCell(2).setWidth("2000");
		table.getRow(0).getCell(2).setText("부제");
		table.getRow(0).getCell(3).setWidth("1000");
		table.getRow(0).getCell(3).setText(column1);
		table.getRow(0).getCell(4).setWidth("1000");
		table.getRow(0).getCell(4).setText("파일명");
		table.getRow(0).getCell(5).setWidth("800");
		table.getRow(0).getCell(5).setText("강의 시간");
		table.getRow(0).getCell(6).setWidth("500");
		table.getRow(0).getCell(6).setText("문장수");
		table.getRow(0).getCell(7).setWidth("500");
		table.getRow(0).getCell(7).setText("어절수");

		Metadata metadata;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			metadata = list.get(rowIdx);
			table.getRow(rowIdx+1).getCell(0).setWidth("500");
			table.getRow(rowIdx+1).getCell(0).setText(Integer.toString(metadata.getId()));
			table.getRow(rowIdx+1).getCell(1).setWidth("2000");
			table.getRow(rowIdx+1).getCell(2).setWidth("2000");
			if(metadata.getProgram()!= null) {
				table.getRow(rowIdx+1).getCell(1).setText(metadata.getProgram().getTitle());
				table.getRow(rowIdx+1).getCell(2).setText(metadata.getProgram().getSubtitle());
			}
			table.getRow(rowIdx+1).getCell(3).setWidth("1000");
			table.getRow(rowIdx+1).getCell(3).setText(metadata.getCreator());
			table.getRow(rowIdx+1).getCell(4).setWidth("1000");
			table.getRow(rowIdx+1).getCell(4).setText(metadata.getTitle());
			table.getRow(rowIdx+1).getCell(5).setWidth("800");
			if(metadata.getProgram()!= null) {
				table.getRow(rowIdx+1).getCell(5).setText(metadata.getProgram().getRunning_time());
			}
			table.getRow(rowIdx+1).getCell(6).setWidth("500");
			table.getRow(rowIdx+1).getCell(6).setText(Integer.toString(metadata.getSentence_count()));
			table.getRow(rowIdx+1).getCell(7).setWidth("500");
			table.getRow(rowIdx+1).getCell(7).setText(Integer.toString(metadata.getEojeol_total()));
		}

		/* 입력된 내용 파일로 쓰기 */
		File file = new File(path + docxFileName);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			doc.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			if(flag.equals("download")) {
				response.setHeader("Content-Disposition", "attachment;filename=" + docxFileName);
				response.setContentType("application/octet-stream; charset=utf-8");
				doc.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			/* 사용자 mail로 파일전송 */
			else if(flag.subSequence(0,4).equals("mail")){
				ms.sendMail(file, docxFileName, flag.substring(4,flag.length()));
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if(doc!=null) doc.close();
				if(fos!=null) fos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	/**
	 * docx utterance 리스트 작성
	 * @param response 사용자에게 보내는 응답
	 * @param path 파일 디렉토리
	 * @param list 객체를 담을 리스트
	 * @param metadata metadata 테이블 정보
	 * @param flag 해당 요청이 download인지, mail인지, sms인지 결정해주는 변수
	 * @throws Exception 예외처리
	 */
	public void writeDocxUtterance(HttpServletResponse response, String path, List<Utterance> list, Metadata metadata, String flag)throws Exception {
		String docxFileName =
				metadata.getTitle()+ "_" +
						new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".docx";
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		/* doc 파일 생성 */
		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();

		r.setText("날짜 : " + day);
		r.setFontSize(9);
		r.addBreak();r.addBreak();

		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		if(metadata.getProgram() != null) {
			r1.setText(metadata.getProgram().getTitle()+"  "+metadata.getProgram().getSubtitle());
		}
		r1.setBold(true);
		r1.setFontSize(14);
		r1.addBreak();
		XWPFRun r2 = p1.createRun();
		if(metadata.getProgram() != null) {
			r2.setText("running time: " + metadata.getProgram().getRunning_time());
		}
		r2.addBreak();
		r2.setText("creator: " + metadata.getCreator());
		r2.setFontSize(10);
		r2.addBreak();


		XWPFTable table = doc.createTable(list.size()+1, 5);
		table.setWidth("100%");

		/* 헤더 정보 구성 */
		table.getRow(0).getCell(0).setWidth("600");
		table.getRow(0).getCell(0).setText(column0);
		table.getRow(0).getCell(1).setWidth("6000");
		table.getRow(0).getCell(1).setText("form");
		table.getRow(0).getCell(2).setWidth("800");
		table.getRow(0).getCell(2).setText("시작시간(단위: 초)");
		table.getRow(0).getCell(3).setWidth("800");
		table.getRow(0).getCell(3).setText("종료시간(단위: 초)");
		table.getRow(0).getCell(4).setWidth("600");
		table.getRow(0).getCell(4).setText("어절수");

		Utterance utterance;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			utterance = list.get(rowIdx);
			table.getRow(rowIdx+1).getCell(0).setWidth("600");
			table.getRow(rowIdx+1).getCell(0).setText(Integer.toString(rowIdx+1));
			table.getRow(rowIdx+1).getCell(1).setWidth("6000");
			table.getRow(rowIdx+1).getCell(1).setText(utterance.getForm());
			table.getRow(rowIdx+1).getCell(2).setWidth("800");
			table.getRow(rowIdx+1).getCell(2).setText(Integer.toString((int)utterance.getStart()));
			table.getRow(rowIdx+1).getCell(3).setWidth("800");
			table.getRow(rowIdx+1).getCell(3).setText(Integer.toString((int)utterance.getFinish()));
			table.getRow(rowIdx+1).getCell(4).setWidth("600");
			table.getRow(rowIdx+1).getCell(4).setText(Integer.toString((int)utterance.getEojeol_count()));
		}

		// 입력된 내용 파일로 쓰기
		File file = new File(path + docxFileName);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			doc.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			if(flag.equals("download")) {
				response.setHeader("Content-Disposition", "attachment;filename=" + docxFileName);
				response.setContentType("application/octet-stream; charset=utf-8");
				doc.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			/* 사용자 mail로 파일 전송 */
			else if(flag.subSequence(0,4).equals("mail")){
				ms.sendMail(file, docxFileName, flag.substring(4,flag.length()));
			}
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if(doc!=null) doc.close();
				if(fos!=null) fos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}

	/**
	 * rule을 워드로 다운받기 위한 메서드
	 * @param response 파일 다운을 위한 응답
	 * @param ruleList 워드로 다운받을 rule
	 * @param path 파일을 다운받기 위해 임시 저장할 경로
	 */
	public void resultRuleDocx(HttpServletResponse response, List<Rule> ruleList, String path, String name){
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		String day = clientInfo.getTime();
		String docxFileName = day.replace(" ", "_");
		docxFileName = docxFileName.replace(":", "_");
		docxFileName = docxFileName+ ".docx";
		Rule rule;

		/* doc 파일 생성 */
		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph xwpfParagraph = doc.createParagraph();
		xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun xwpfRun = xwpfParagraph.createRun();
		xwpfRun.setText("목차");
		xwpfRun.setBold(true);
		xwpfRun.setFontSize(25);
		xwpfRun.addBreak();
		xwpfRun.addBreak();

		XWPFParagraph xwpfParagraph1 = doc.createParagraph();
		xwpfParagraph1.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun xwpfRun1 = xwpfParagraph1.createRun();
		for(int i=0; i<ruleList.size(); i++) {
			xwpfRun1.setText((i+1) +". "+ ruleList.get(i).getBottom_level_name());
			xwpfRun1.addBreak();
			xwpfRun1.addBreak();
			xwpfRun1.addBreak();
		}
		xwpfRun1.setFontSize(15);
		xwpfRun1.addBreak(BreakType.PAGE);

		for(int x=0; x<ruleList.size(); x++) {
			rule = ruleList.get(x);

			XWPFParagraph p = doc.createParagraph();
			
			p.setAlignment(ParagraphAlignment.LEFT);

			if(x==0){
				XWPFRun r = p.createRun();
				r.setText("작성일 : " + day);
				r.setFontSize(9);
				r.addBreak();
				r.setText("작성자 : " + name);
				r.addBreak();
			}
			XWPFRun r2 = p.createRun();
			r2.setText("대분류 : " + rule.getTop_level_name());
			r2.setFontSize(9);
			r2.addBreak();
			r2.setText("중분류 : " + rule.getMiddle_level_name());
			r2.addBreak();
			if(rule.getDescription() != ""){
				r2.setText("설명 : " + rule.getDescription());
				r2.addBreak();
			}

			XWPFParagraph p1 = doc.createParagraph();
			p1.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r1 = p1.createRun();

			if (rule.getBottom_level_name() != null) {
				r1.setText(rule.getBottom_level_name());
			}
			r1.setBold(true);
			r1.setFontSize(14);
			r1.addBreak();

			XWPFTable table = null;
			List<String> list;
			List<String> strList;
			// result가 null일 경우
			if (rule.getResult() == null || rule.getResult().length() == 0) {
				XWPFRun r0 = p1.createRun();
				r0.setText("~ 해당되는 결과를 찾을 수 없습니다. ~");
				r0.setFontSize(11);
				r0.addBreak();
			}
			// rule의 result가 배열일 경우 테이블 생성하여 출력
			else if (rule.getResult().charAt(0) == '[') {
				String ruleStr = rule.getResult().substring(2, rule.getResult().length() - 2);
				list = Arrays.asList(ruleStr.split("], \\["));
				if (rule.getResult() != null) {
					for (int j = 0; j < list.size(); j++) {
						strList = Arrays.asList(list.get(j).split(", "));
						if (j == 0) {
							table = doc.createTable(list.size(), strList.size());
						}
						for (int i = 0; i < strList.size(); i++) {
							double width = 8300.0 / strList.size();
							table.getRow(j).getCell(i).setWidth(Integer.toString((int) Math.ceil(width)));
							table.getRow(j).getCell(i).getParagraphArray(0).setSpacingAfter(0);
							XWPFParagraph tempParagraph = table.getRow(j).getCell(i).getParagraphs().get(0);
							tempParagraph.setAlignment(ParagraphAlignment.CENTER);
							XWPFRun tempRun = tempParagraph.createRun();
							tempRun.setFontSize(9);
							tempRun.setText(strList.get(i));
							table.getRow(j).getCell(i).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
						}
					}
					XWPFParagraph p00 = doc.createParagraph();
					p00.setAlignment(ParagraphAlignment.LEFT);
					XWPFRun r00 = p00.createRun();
					r00.setText("");
					r00.setFontSize(10);
					r00.addBreak();
					ChartHelper chartHelper = new ChartHelper();
					doc = chartHelper.checkChartForm(doc, rule, path);
				}
			}
			// rule의 result가 배열이 아닌경우 1x1 표로 결과 출력
			else {
				table = doc.createTable(1, 1);
				table.getRow(0).getCell(0).setWidth(Integer.toString((int) Math.ceil(8300)));
				table.getRow(0).getCell(0).getParagraphArray(0).setSpacingAfter(0);
				XWPFParagraph tempParagraph = table.getRow(0).getCell(0).getParagraphs().get(0);
				tempParagraph.setAlignment(ParagraphAlignment.CENTER);
				XWPFRun tempRun = tempParagraph.createRun();
				tempRun.setFontSize(9);
				tempRun.setText(rule.getResult());
				table.getRow(0).getCell(0).setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
			}
			
			/* 하단 여백 및 구분선 추가(맨 마지막 표 제외) */
			if(x<ruleList.size()-1) {
				XWPFParagraph p01 = doc.createParagraph();
				p01.setAlignment(ParagraphAlignment.LEFT);
				XWPFRun r01 = p01.createRun();
 				r01.addBreak(BreakType.PAGE);
			}
		}

		XWPFParagraph p1;
		XWPFParagraph[] pars;

		// create footer
		CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc, sectPr);
		CTP ctpFooter = CTP.Factory.newInstance();

		XWPFParagraph[] parsFooter;

		// add style (s.th.)
		CTPPr ctppr = ctpFooter.addNewPPr();
		CTString pst = ctppr.addNewPStyle();
		pst.setVal("style21");
		CTJc ctjc = ctppr.addNewJc();
		ctjc.setVal(STJc.CENTER);
		ctppr.addNewRPr();

		// Add in word "Page "
		CTR ctr = ctpFooter.addNewR();
		CTText t = ctr.addNewT();
		t.setStringValue("-");
		t.setSpace(SpaceAttribute.Space.PRESERVE);

		// add everything from the footerXXX.xml you need
		ctr = ctpFooter.addNewR();
		ctr.addNewRPr();
		CTFldChar fch = ctr.addNewFldChar();
		fch.setFldCharType(STFldCharType.BEGIN);

		ctr = ctpFooter.addNewR();
		ctr.addNewInstrText().setStringValue(" PAGE ");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

		ctpFooter.addNewR().addNewT().setStringValue("1");

		ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

		ctr = ctpFooter.addNewR();
		ctr.addNewT().setStringValue("-");

		XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);

		parsFooter = new XWPFParagraph[1];

		parsFooter[0] = footerParagraph;

		policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);


		/* 입력된 내용 파일로 쓰기 */
		File file = new File(path + docxFileName);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(file);
			doc.write(fos);

//			/* 사용자 컴퓨터에 다운로드 */
			byte fileByte[] = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(docxFileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);

			response.getOutputStream().flush();
			response.getOutputStream().close();

			//file.delete();

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if(doc!=null) doc.close();
				if(fos!=null) fos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
	}
	
	/**
	 * 룰 기록 조회에서 이전에 다운받았던 룰 보고서 파일을 다운 받음
	 * @param response HttpServletResponse
	 * @param path 이전 룰 보고서 파일이 있는 폴더
	 * @param time 파일명(이전에 다운받은 시간)
	 * @throws UnsupportedEncodingException 지원되지 않는 인코딩 예외
	 */
	public void downloadPrevRuleReport(HttpServletResponse response, String path, String time) {
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		String fileName = time + ".docx";
		File file = new File(path + fileName);
		try { /* 사용자 컴퓨터에 다운로드 */
			if (file.exists() && file.isFile()) {
				byte fileByte[] = FileUtils.readFileToByteArray(file);
				response.setContentType("application/octet-stream");
				response.setContentLength(fileByte.length);
				response.setHeader("Content-Disposition", "attachment; fileName=\""+ URLEncoder.encode(fileName,"UTF-8")+"\";");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.getOutputStream().write(fileByte);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}

	public List<List<String>> parsing(String result){
		List<List<String>> listList = new ArrayList<>();
		List<String> list = new ArrayList<>();
		List<String> stringList = new ArrayList<>();

		stringList = Arrays.asList(result.substring(2,result.length()-2).split("}, \\{"));

		for(String str : stringList){
			list = Arrays.asList(str.split("="));
		}


		return listList;
	}

	public String is_Null(String str){
		return (str == "") ? " " : str;
	}
}
