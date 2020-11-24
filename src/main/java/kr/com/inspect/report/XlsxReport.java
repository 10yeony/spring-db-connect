package kr.com.inspect.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import kr.com.inspect.sender.SendReport;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;

/**
 * Xlsx 타입으로 리스트 파일 생성
 * @author Woo Young
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Service
@PropertySource(value = "classpath:properties/report.properties")
public class XlsxReport {

	/**
	 * 메일과 sms 전송을 위한 SendReport 필드 선언
	 */
	@Autowired
	private SendReport ms;

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
	 * xlsx 한국어 강의 목록 리스트 작성
	 * @param response 사용자에게 보내는 응답
	 * @param path 파일 디렉토리
	 * @param list 객체를 담을 리스트
	 * @param flag 해당 요청이 download인지, mail인지, sms인지 결정해주는 변수
	 * @throws Exception 예외처리
	 */
	public void writeXlsxMetadata(HttpServletResponse response, String path, List<Metadata> list, String flag) throws Exception {
		String xlsxFileName =
				"LectureList_"+
				new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".xlsx"; //파일명
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		/**
		 * xlsx 파일 생성 
		 */
		XSSFWorkbook workbook = new XSSFWorkbook(); //워크북
		XSSFSheet sheet = workbook.createSheet(); //워크시트
		XSSFRow row = sheet.createRow(0); //행
		XSSFCell cell; //셀
		cell = row.createCell(0);
		cell.setCellValue("날짜 : " + day);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("한국어 강의 목록 리스트");
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));


		row = sheet.createRow(4);
		/* 헤더 정보 구성 */
		cell = row.createCell(0);
		cell.setCellValue(column0);
		cell = row.createCell(1);
		cell.setCellValue("제목");
		cell = row.createCell(2);
		cell.setCellValue("부제");
		cell = row.createCell(3);
		cell.setCellValue(column1);
		cell = row.createCell(4);
		cell.setCellValue("파일명");
		cell = row.createCell(5);
		cell.setCellValue("강의시간");
		cell = row.createCell(6);
		cell.setCellValue("문장수");
		cell = row.createCell(7);
		cell.setCellValue("어절수");

		// 리스트의 size 만큼 row를 생성
		Metadata metadata;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			metadata = list.get(rowIdx);
			row = sheet.createRow(rowIdx+5); //행 생성
			cell = row.createCell(0);
			cell.setCellValue(metadata.getId());
			cell = row.createCell(1);
			cell.setCellValue(metadata.getProgram().getTitle());
			cell = row.createCell(2);
			cell.setCellValue(metadata.getProgram().getSubtitle());
			cell = row.createCell(3);
			cell.setCellValue(metadata.getCreator());
			cell = row.createCell(4);
			cell.setCellValue(metadata.getTitle());
			cell = row.createCell(5);
			cell.setCellValue(metadata.getProgram().getRunning_time());
			cell = row.createCell(6);
			cell.setCellValue(Integer.toString(metadata.getSentence_count()));
			cell = row.createCell(7);
			cell.setCellValue(Integer.toString(metadata.getEojeol_total()));
		}

		// 입력된 내용 파일로 쓰기
		String fullPath = path + xlsxFileName;
		File file = new File(fullPath);
		FileOutputStream fos = null;


		try {
			fos = new FileOutputStream(fullPath);
			workbook.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			if(flag.equals("download")) {
				response.setHeader("Content-Disposition", "attachment;filename=" + xlsxFileName);
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setContentType("application/vnd.ms-excel");
				workbook.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			/* 사용자 mail로 파일전송 */
			else if(flag.substring(0,4).equals("mail")){
				ms.sendMail(file, xlsxFileName, flag.substring(4,flag.length()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * xlsx utterance 리스트 작성
	 * @param response 사용자에게 보내는 응답
	 * @param path 파일 디렉토리
	 * @param list 객체를 담을 리스트
	 * @param metadata metadata 테이블 정보
	 * @param flag 해당 요청이 download인지, mail인지, sms인지 결정해주는 변수
	 * @throws Exception 예외 처리
	 */
	public void writeXlsxUtterance(HttpServletResponse response, String path, List<Utterance> list, Metadata metadata, String flag)throws Exception {
		String xlsxFileName =
				metadata.getTitle()+ "_" +
						new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
						+ ".xlsx"; //파일명
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		/* xlsx 파일 생성 */
		XSSFWorkbook workbook = new XSSFWorkbook(); //워크북
		XSSFSheet sheet = workbook.createSheet(); //워크시트
		XSSFRow row = sheet.createRow(0); //행
		XSSFCell cell; //셀
		cell = row.createCell(0);
		cell.setCellValue("날짜 : " + day);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

		// 강의명과 부제 출력
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(metadata.getProgram().getTitle()+"  "+metadata.getProgram().getSubtitle());
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,3));

		// running time
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue("running time: " + metadata.getProgram().getRunning_time());
		sheet.addMergedRegion(new CellRangeAddress(3,3,0,1));

		// creator
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("creator: " + metadata.getCreator());
		sheet.addMergedRegion(new CellRangeAddress(4,4,0,1));

		row = sheet.createRow(6);
		/* 헤더 정보 구성 */
		cell = row.createCell(0);
		cell.setCellValue(column0);
		cell = row.createCell(1);
		cell.setCellValue("form");
		cell = row.createCell(2);
		cell.setCellValue("시작시간(단위: 초)");
		cell = row.createCell(3);
		cell.setCellValue("종료시간(단위: 초)");
		cell = row.createCell(4);
		cell.setCellValue("어절수");

		// 리스트의 size 만큼 row를 생성
		Utterance utterance;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			utterance = list.get(rowIdx);
			row = sheet.createRow(rowIdx+7); //행 생성
			cell = row.createCell(0);
			cell.setCellValue(Integer.toString(rowIdx+1));
			cell = row.createCell(1);
			cell.setCellValue(utterance.getForm());
			cell = row.createCell(2);
			cell.setCellValue(utterance.getStart());
			cell = row.createCell(3);
			cell.setCellValue(utterance.getFinish());
			cell = row.createCell(4);
			cell.setCellValue(utterance.getEojeol_count());
		}

		// 입력된 내용 파일로 쓰기
		String fullPath = path + xlsxFileName;
		File file = new File(fullPath);
		FileOutputStream fos = null;


		try {
			fos = new FileOutputStream(fullPath);
			workbook.write(fos);

			/* 사용자 컴퓨터에 다운로드 */
			if(flag.equals("download")) {
				response.setHeader("Content-Disposition", "attachment;filename=" + xlsxFileName);
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setContentType("application/vnd.ms-excel");
				workbook.write(response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			/* 사용자 mail로 파일 전송 */
			else if(flag.substring(0,4).equals("mail")){
				ms.sendMail(file, xlsxFileName, flag.substring(4, flag.length()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
