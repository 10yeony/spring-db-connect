package kr.com.inspect.report;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import kr.com.inspect.dto.Rule;

/**
 * 워드 파일에 룰 결과 차트를 작성하는 데 도와주는 객체
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 *
 */
public class ChartHelper {
	/**
	 * 임시 차트 이미지 파일을 저장할 경로
	 */
	private String path;
	
	/**
	 * 임시 차트 이미지 파일 이름
	 */
	private String chartImgName = "temp.jpg";
	
	/**
	 * 차트 제목
	 */
	private String title;
	
	/**
	 * 데이터 목록
	 */
	private List<List<String>> list;
	
	/**
	 * 데이터의 행(row)의 수
	 */
	private int rowCount;
	
	/**
	 * 데이터의 열(column)의 수
	 */
	private int columnCount;
	
	/**
	 * 차트 가로 길이
	 */
	private int chartWidth = 800;
	
	/**
	 * 차트 세로 길이
	 */
	private int chartHeight = 600;
	
	/**
	 * 문서에 들어갈 차트 가로 길이
	 */
	private int insertChartWidth = 400;
	
	/**
	 * 문서에 들어갈 차트 세로 길이
	 */
	private int insertChartHeight = 300;
	
	/**
	 * 데이터를 체크하여 알맞은 형태의 차트를 작성하게끔 함
	 * @param doc 워드 문서 객체
	 * @param rule 전사 규칙 객체
	 * @param path 임시 차트 이미지 파일을 저장할 경로
	 * @return 차트 이미지가 추가된 워드 문서 객체
	 */
	public XWPFDocument checkChartForm(XWPFDocument doc, Rule rule, String path) {
		this.path = path;
		this.title = rule.getBottom_level_name(); 
		String ruleResult = rule.getResult().substring(2, rule.getResult().length()-2);
		String[] ruleResultArr = ruleResult.split("], \\[");
		this.list = new ArrayList<>(); 
		for(int i=0; i<ruleResultArr.length; i++) {
			list.add(Arrays.asList(ruleResultArr[i].split(", ")));
		}
		this.rowCount = list.size()-1; 
		this.columnCount = ruleResultArr[0].split(", ").length-1; 
		boolean containsChar = false; //값이 문자를 포함하고 있는지 여부
		for(int i=1; i<columnCount+1; i++) {
			try {
				if(rowCount == 1) {
					Double.parseDouble(list.get(1).get(i).substring(0, list.get(1).get(i).length()-1));
					if(list.get(1).get(i).charAt(list.get(1).get(i).length()-1) == '%') {
						drawPieChartAboutRuleResult(doc);
						break;
					}
				}
				Double.parseDouble(list.get(1).get(i));
			}catch(Exception e) {
				containsChar = true;
				break;
			}
		}
		if(!containsChar && rowCount <= 10) {
			doc = drawBarChartAboutRuleResult(doc);
		}
		return doc;
	}
	
	/**
	 * 막대그래프를 작성함
	 * @param doc 워드 문서 객체
	 * @return 막대그래프가 추가된 워드 문서 객체
	 */
	public XWPFDocument drawBarChartAboutRuleResult(XWPFDocument doc) {
		try {
			String xAxisTitle = list.get(0).get(0);
			CategoryChart chart = new CategoryChartBuilder().width(chartWidth).height(chartHeight).theme(Styler.ChartTheme.GGPlot2).title(title).xAxisTitle(xAxisTitle).yAxisTitle("").build();
			
			chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
			chart.getStyler().setAvailableSpaceFill(.96); //막대 간 간격
//			chart.getStyler().setOverlapped(true); //막대 겹치게 
			chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
			chart.getStyler().setHasAnnotations(true);
			
			String[] xDataArr = (String[]) getXYData("multiple").get("xDataArr");
			Number[][] yDataArr = (Number[][]) getXYData("multiple").get("yDataArr");
			for(int i=0; i<columnCount; i++) {
				Number[] thisYDataArr = new Number[rowCount];
				for(int j=0; j<rowCount; j++) {
					thisYDataArr[j] = yDataArr[j][i];
				}
				chart.addSeries(list.get(0).get(i+1), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(thisYDataArr)));
				//System.out.println(new ArrayList<Number>(Arrays.asList(thisYDataArr)));
			}
			
			BitmapEncoder.saveBitmapWithDPI(chart, this.path+chartImgName, BitmapEncoder.BitmapFormat.JPG, 300);
			doc.createParagraph().createRun().addPicture(new FileInputStream(this.path+chartImgName), XWPFDocument.PICTURE_TYPE_JPEG, chartImgName, Units.toEMU(insertChartWidth), Units.toEMU(insertChartHeight));
			new File(path+chartImgName).delete();
		}catch (Exception e){
			//e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 원그래프를 작성함
	 * @param doc 워드 문서 객체
	 * @return 원그래프가 추가된 워드 문서 객체
	 */
	public XWPFDocument drawPieChartAboutRuleResult(XWPFDocument doc) {
		try {
			String xAxisTitle = list.get(0).get(0);
			PieChart chart = new PieChartBuilder().width(chartWidth).height(chartHeight).theme(Styler.ChartTheme.GGPlot2).title(title).build();
			
			chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
			chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
			chart.getStyler().setHasAnnotations(true);
			
			Random random = new Random();
			Color[] sliceColors = new Color[columnCount];
			for(int i=0; i<columnCount; i++) {
				sliceColors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
			}
			chart.getStyler().setSeriesColors(sliceColors);
			
			String[] xDataArr = (String[]) getXYData("oneRow").get("xDataArr");
			Number[] yDataArr = (Number[]) getXYData("oneRow").get("yDataArr");
			for(int i=0; i<columnCount; i++) {
				chart.addSeries(xDataArr[i], yDataArr[i]);
			}
			
			BitmapEncoder.saveBitmapWithDPI(chart, this.path+chartImgName, BitmapEncoder.BitmapFormat.JPG, 300);
			doc.createParagraph().createRun().addPicture(new FileInputStream(this.path+chartImgName), XWPFDocument.PICTURE_TYPE_JPEG, chartImgName, Units.toEMU(insertChartWidth), Units.toEMU(insertChartHeight));
			new File(path+chartImgName).delete();
		}catch (Exception e){
			//e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * 차트에 들어갈 x축, y축 데이터를 정리하여 담아서 리턴함
	 * @param tableType 테이블 유형
	 * @return 정리된 차트에 들어갈 x축, y축 데이터
	 */
	public Map<String, Object> getXYData(String tableType) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(tableType.equals("multiple")) {
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
			map.put("xDataArr", xDataArr);
			map.put("yDataArr", yDataArr);
		}else if(tableType.equals("oneRow")) {
			String[] xDataArr = new String[columnCount];
			Number[] yDataArr = new Number[columnCount];
			for(int i=1; i<columnCount+1; i++) {
				xDataArr[i-1] = list.get(0).get(i);
				yDataArr[i-1] = Double.parseDouble(list.get(1).get(i).substring(0, list.get(1).get(i).length()-1));
			}
			map.put("xDataArr", xDataArr);
			map.put("yDataArr", yDataArr);
		}
		return map;
	}
	
//	public static void main(String[] args) {
//		ChartHelper chartHelper = new ChartHelper();
//		Rule rule = new Rule();
//		rule.setBottom_level_name("아스키코드 이외의 기호를 사용한 전사자");
//		//rule.setResult("[[전사자, 데이터1, 데이터2, 데이터3, 데이터4, 데이터5], [이민지(als3o@naver.com), 10%, 15%, 25%, 35%, 15%]]");
//		//rule.setResult("[[전사자, 맞음, 틀림], [이민지(als3o@naver.com), 맞는 내용, 틀린 내용]]");
//		rule.setResult("[[학년, 여 (단위: 분), 남 (단위: 분), 미기입 (단위: 분)], [1, 4313.54, 1678.85, 1629.71], [2, 2427.36, 2141.98, 1453.73], [3, 1557.38, 6862.85, 2519.35]]");
//		
//		XWPFDocument doc = new XWPFDocument();
//		String path = "/home/namu/Documents/test/report/docx/";
//		doc = chartHelper.checkChartForm(doc, rule, path);
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
