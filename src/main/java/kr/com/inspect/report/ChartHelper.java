package kr.com.inspect.report;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import kr.com.inspect.dto.Rule;

public class ChartHelper {
	public XWPFDocument addHistogramAboutRuleResult(XWPFDocument doc, Rule rule, String path) {
		String title = rule.getBottom_level_name();
		String ruleResult = rule.getResult().substring(2, rule.getResult().length()-2);
		String[] ruleResultArr = ruleResult.split("], \\[");
		List<List<String>> list = new ArrayList<>();
		for(int i=0; i<ruleResultArr.length; i++) {
			list.add(Arrays.asList(ruleResultArr[i].split(", ")));
		}
		int rowCount = list.size()-1;
		int columnCount = ruleResultArr[0].split(", ").length-1;
		
		try {
			String xAxisTitle = list.get(0).get(0);
			CategoryChart chart = new CategoryChartBuilder().width(800).height(600).theme(Styler.ChartTheme.GGPlot2).title(title).xAxisTitle(xAxisTitle).yAxisTitle("").build();
			
			chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
			chart.getStyler().setAvailableSpaceFill(.96); //막대 간 간격
//			chart.getStyler().setOverlapped(true); //막대 겹치게 
			chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
			chart.getStyler().setHasAnnotations(true);
			
			String[] xDataArr = new String[rowCount];
			Number[][] yDataArr = new Number[rowCount][columnCount];
			for(int i=0; i<rowCount+1; i++) {
				if(i==0) continue;
				xDataArr[i-1] = list.get(i).get(0);
			}
			for(int i=0; i<columnCount+1; i++) {
				for(int j=0; j<rowCount+1; j++) {
					if(i==0 || j==0) continue;
					yDataArr[j-1][i-1] = Double.parseDouble(list.get(j).get(i));
				}
			}
			//System.out.println(new ArrayList<String>(Arrays.asList(xDataArr)));
			for(int i=0; i<columnCount; i++) {
				Number[] thisYDataArr = new Number[rowCount];
				for(int j=0; j<rowCount; j++) {
					thisYDataArr[j] = yDataArr[j][i];
				}
				chart.addSeries(list.get(0).get(i+1), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(thisYDataArr)));
				//System.out.println(new ArrayList<Number>(Arrays.asList(thisYDataArr)));
			}
			
			BitmapEncoder.saveBitmapWithDPI(chart, path+"tmp.jpg", BitmapEncoder.BitmapFormat.JPG, 300);
			doc.createParagraph().createRun().addPicture(new FileInputStream(path+"/tmp.jpg"), XWPFDocument.PICTURE_TYPE_JPEG, "tmp.jpg", Units.toEMU(500), Units.toEMU(400));
			new File(path+"tmp.jpg").delete();
		}catch (Exception e){
			//e.printStackTrace();
		}
		return doc;
	}
	
//	public static void main(String[] args) {
//		ChartHelper chartHelper = new ChartHelper();
//		Rule rule = new Rule();
//		rule.setBottom_level_name("아스키코드 이외의 기호를 사용한 전사자");
//		rule.setResult("[[전사자, 아스키코드 이외의 기호를 사용한 문장수, 입력한 총 문장수], [이민지(als3o@naver.com), 1, 4857], [김희경(banila778@gmail.com), 1, 2370], [김우진(biff4933@gmail.com), 2, 902], [조원기(chowk1109@naver.com), 1, 6005], [홍철(cjfl2564@naver.com), 1, 2932], [김수현(clousy23@gmail.com), 1, 2129], [박찬석(ict.cspark@gmail.com), 1, 4074], [문영웅(moonoo3@naver.com), 1, 3264], [정준영(suj710101@gmail.com), 2, 8494], [김효원(sun4131@gmail.com), 3, 4767]]");
//		//XWPFDocument doc = chartHelper.writeXlsx(rule);
//		XWPFDocument doc = new XWPFDocument();
//		String path = "/home/namu/Documents/test/report/docx/";
//		doc = chartHelper.addHistogramAboutRuleResult(doc, rule, path);
//		FileOutputStream fileOutputStream;
//		try {
//			fileOutputStream = new FileOutputStream("/home/namu/Documents/doc1.docx");
//			doc.write(fileOutputStream);
//			doc.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
