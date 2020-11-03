package kr.com.inspect.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Sound;

@Service
@PropertySource(value = "classpath:report.properties")
public class XlsxReport {
	@Value("${table.column0}")
	private String column0;

	@Value("${table.column1}")
	private String column1;

	@Value("${table.column2}")
	private String column2;

	@Value("${table.column3}")
	private String column3;

	@Value("${table.column4}")
	private String column4;

	public void writeXlsx(String path, List<Sound> list) {
		String xlsxFileName =
				new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
						+ "_log.xlsx"; //파일명
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		XSSFWorkbook workbook = new XSSFWorkbook(); //워크북
		XSSFSheet sheet = workbook.createSheet(); //워크시트
		XSSFRow row = sheet.createRow(0); //행
		XSSFCell cell; //셀
		cell = row.createCell(0);
		cell.setCellValue("날짜 : " + day);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("제목");
		sheet.addMergedRegion(new CellRangeAddress(2,2,0,1));


		row = sheet.createRow(4);
		/* 헤더 정보 구성 */
		cell = row.createCell(0);
		cell.setCellValue(column0);
		cell = row.createCell(1);
		cell.setCellValue(column1);
		cell = row.createCell(2);
		cell.setCellValue(column2);
		cell = row.createCell(3);
		cell.setCellValue(column3);
		cell = row.createCell(4);
		cell.setCellValue(column4);

		// 리스트의 size 만큼 row를 생성
		Sound vo;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			vo = list.get(rowIdx);
			row = sheet.createRow(rowIdx+5); //행 생성
			cell = row.createCell(0);
			cell.setCellValue(vo.getId());
			cell = row.createCell(1);
			cell.setCellValue(vo.getCategory());
			cell = row.createCell(2);
			cell.setCellValue(vo.getTitle());
			cell = row.createCell(3);
			cell.setCellValue(vo.getCompany());
			cell = row.createCell(4);
			cell.setCellValue(vo.getContent());
		}

		// 입력된 내용 파일로 쓰기
		String fullPath = path + xlsxFileName;
		File file = new File(fullPath);
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fullPath);
			workbook.write(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
