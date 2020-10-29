package kr.com.inspect.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import kr.com.inspect.dto.Sound;
import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;

@Service
@PropertySource(value = "classpath:report.properties") 
public class HwpReport {
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
	
	public void writeHwp(String path, List<Sound> list) {
		String hwpFileName = 
				new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()) 
				+ "_log.hwp";
		
		try {
			HWPFile hwpFile = BlankFileMaker.make( );
			ArrayList<Section> sList = hwpFile.getBodyText( ).getSectionList( );
			System.out.println(sList.size());
			
			Sound vo;
			for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
				vo = list.get(rowIdx);
				Paragraph p = sList.get(0).getParagraph(rowIdx);
				p.getText().addString(vo.toString());
			}			
			HWPWriter.toFile(hwpFile, path+hwpFileName);
			System.out.println(path + hwpFileName);
		}catch(Exception e) {
			//e.printStackTrace();
		}
	}
}