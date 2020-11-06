package kr.com.inspect.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.com.inspect.dto.Metadata;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Sound;

@Service
@PropertySource(value = "classpath:properties/report.properties")
public class DocxReport {
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

	@Value("${table.column5}")
	private String column5;

	@Value("${table.column6}")
	private String column6;

	@Value("${table.column7}")
	private String column7;

	@Value("${table.column8}")
	private String column8;

	/* docx 보고서 작성 */
	public void writeDocx(String path, List<Metadata> list) {
		String docxFileName =
				new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())
						+ "_log.docx";
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		XWPFDocument doc = new XWPFDocument();

		XWPFParagraph p = doc.createParagraph();
		XWPFRun r = p.createRun();

		r.setText("날짜 : " + day);
		r.setFontSize(13);
		r.addBreak();r.addBreak();

		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setText("제목");
		r1.setBold(true);
		r1.setFontSize(25);
		r1.addBreak();
		r1.addBreak();


		XWPFTable table = doc.createTable(list.size()+1, 7);

		/* 헤더 정보 구성 */
		table.getRow(0).getCell(0).setText(column0);
		table.getRow(0).getCell(1).setText("title");
		table.getRow(0).getCell(2).setText("subtitle");
		table.getRow(0).getCell(3).setText(column1);
		table.getRow(0).getCell(4).setText(column3);
		table.getRow(0).getCell(5).setText("file_num");
		table.getRow(0).getCell(6).setText("running_time");

		Metadata metadata;
		for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
			metadata = list.get(rowIdx);
			table.getRow(rowIdx+1).getCell(0).setText(Integer.toString(metadata.getId()));
			table.getRow(rowIdx+1).getCell(1).setText(metadata.getProgram().getTitle());
			table.getRow(rowIdx+1).getCell(2).setText(metadata.getProgram().getSubtitle());
			table.getRow(rowIdx+1).getCell(3).setText(metadata.getCreator());
			table.getRow(rowIdx+1).getCell(4).setText(metadata.getYear());
			table.getRow(rowIdx+1).getCell(5).setText(metadata.getTitle());
			table.getRow(rowIdx+1).getCell(6).setText(metadata.getProgram().getRunning_time());
		}

		// 입력된 내용 파일로 쓰기
		File file = new File(path + docxFileName);
		FileOutputStream fos = null;
		System.out.println(path + docxFileName);

		try {
			fos = new FileOutputStream(file);
			doc.write(fos);
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

	public String is_Null(String str){
		return (str == "") ? " " : str;
	}
}
