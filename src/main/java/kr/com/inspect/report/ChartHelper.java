package kr.com.inspect.report;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BoxChart;
import org.knowm.xchart.BoxChartBuilder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.BoxStyler.BoxplotCalCulationMethod;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;

import kr.com.inspect.dto.Rule;

/**
 * 워드 파일에 룰 결과 차트를 작성하는 데 도와주는 객체
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
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
	 * 원본 데이터 목록
	 */
	private List<List<String>> list;
	
	/**
	 * 정돈된 데이터 목록
	 */
	private Map<String, Object> map;
	
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
	 * row가 한 개인지 여부
	 */
	private boolean isOneRow;
	
	/**
	 * column이 한 개인지 여부
	 */
	private boolean isOneColumn;
	
	/***
	 * x축에 있는 것이 한 개인지 여부
	 */
	private boolean isOneX;
	
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
		try {
			byte utf8Bytes[] = ruleResult.getBytes("utf-8");
			ruleResult = new String(utf8Bytes,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			//e1.printStackTrace();
		}
		String[] ruleResultArr = ruleResult.split("], \\[");
		this.list = new ArrayList<>(); 
		for(int i=0; i<ruleResultArr.length; i++) {
			list.add(Arrays.asList(ruleResultArr[i].split(", ")));
		}
		this.rowCount = list.size()-1; 
		this.columnCount = ruleResultArr[0].split(", ").length-1; 
		boolean containsChar = false; //값이 문자를 포함하고 있는지 여부
		boolean containsPercent = false; //값이 %를 포함하는 비율 그래프인지 여부
		for(int i=1; i<columnCount+1; i++) {
			try {
				if(rowCount == 1) {
					Double.parseDouble(list.get(1).get(i).substring(0, list.get(1).get(i).length()-1));
					if(list.get(1).get(i).charAt(list.get(1).get(i).length()-1) == '%') {
						containsChar = true;
						containsPercent = true;
						break;
					}
				}
				Double.parseDouble(list.get(1).get(i));
			}catch(Exception e) {
				containsChar = true;
				break;
			}
		}
		
		/* %가 포함된 비율그래프인 경우 */
		if(containsPercent) {			
			try { //x축 값이 하나일 때
				Double.parseDouble(list.get(1).get(0).substring(0, list.get(1).get(0).length()-1));
				this.isOneRow = true;
				this.isOneColumn = false;
				this.isOneX = true;
			}catch(NumberFormatException e) {
				this.isOneRow = true;
				this.isOneColumn = false;
				this.isOneX = false;
			}finally { 
				setXYData();
				doc = drawPieChart(doc);
			}
		}
		
		/* 값이 숫자로만 되어있는 경우 */
		if(!containsChar) {
			try {
				Double.parseDouble(list.get(1).get(0));
				this.isOneRow = false;
				this.isOneColumn = false;
				this.isOneX = true;

			}catch(NumberFormatException e) {
				this.isOneRow = false;
				this.isOneColumn = false;
				this.isOneX = false;
			}finally {
				setXYData();
				if((columnCount == 1 && rowCount <= 10) ||
					(columnCount == 2 && rowCount <= 7) ||
					(columnCount == 3 && rowCount <= 5)) { //데이터의 개수가 너무 많지 않은 경우
					doc = drawBarChart(doc);
				}
				else { //데이터의 개수가 너무 않은 경우
					doc = drawBoxChart(doc);
				}
			}
		}
		return doc;
	}
	
	/**
	 * 막대그래프를 작성함
	 * @param doc 워드 문서 객체
	 * @return 막대그래프가 추가된 워드 문서 객체
	 */
	public XWPFDocument drawBarChart(XWPFDocument doc) {
		for(int idx=0; idx<getChartCount(); idx++) {
			try {
				String xAxisTitle = "";
				if(!isOneX) xAxisTitle = list.get(0).get(0);
				CategoryChart chart = new CategoryChartBuilder().width(chartWidth).height(chartHeight).theme(Styler.ChartTheme.GGPlot2).title(title).xAxisTitle(xAxisTitle).yAxisTitle("").build();

				chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
				chart.getStyler().setAvailableSpaceFill(.96); //막대 간 간격
				chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
				chart.getStyler().setHasAnnotations(true);

				String[] xDataArr;
				if(isOneX) {
					xDataArr = new String[rowCount];
					for(int i=0; i<rowCount; i++) {
						xDataArr[i] = " ";
					}
				}else {
					xDataArr = (String[]) map.get("xDataArr");
				}
				List<Number[]> yDataList = (List<Number[]>) map.get("yDataList");
				if(getChartCount() > 1 && rowCount > 1) {
					if(isOneX) chart.addSeries(list.get(0).get(idx), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(idx))));
					else chart.addSeries(list.get(0).get(idx+1), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(idx))));
					BitmapEncoder.saveBitmapWithDPI(chart, this.path+chartImgName, BitmapEncoder.BitmapFormat.JPG, 300);
					doc.createParagraph().createRun().addPicture(new FileInputStream(this.path+chartImgName), XWPFDocument.PICTURE_TYPE_JPEG, chartImgName, Units.toEMU(insertChartWidth), Units.toEMU(insertChartHeight));
					new File(path+chartImgName).delete();
				}else {
					for(int i=0; i<yDataList.size(); i++) {
						if(isOneX) chart.addSeries(list.get(0).get(i), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(i))));
						else chart.addSeries(list.get(0).get(i+1), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(i))));
					}
					BitmapEncoder.saveBitmapWithDPI(chart, this.path+chartImgName, BitmapEncoder.BitmapFormat.JPG, 300);
					doc.createParagraph().createRun().addPicture(new FileInputStream(this.path+chartImgName), XWPFDocument.PICTURE_TYPE_JPEG, chartImgName, Units.toEMU(insertChartWidth), Units.toEMU(insertChartHeight));
					new File(path+chartImgName).delete();
					break;
				}
			}catch (Exception e){
				//e.printStackTrace();
			}
		}
		return doc;
	}
	
	/**
	 * 원그래프를 작성함
	 * @param doc 워드 문서 객체
	 * @return 원그래프가 추가된 워드 문서 객체
	 */
	public XWPFDocument drawPieChart(XWPFDocument doc) {
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
			
			String[] xDataArr = (String[]) map.get("xDataArr");
			Number[] yDataArr = (Number[]) map.get("yDataArr");
			for(int i=0; i<columnCount; i++) {
				if(isOneX) {
					chart.addSeries(xDataArr[i], yDataArr[i]);
				}else {
					chart.addSeries(list.get(0).get(i+1), yDataArr[i]);
				}
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
	 *
	 * @param doc
	 * @return
	 */
	public XWPFDocument drawBoxChart(XWPFDocument doc) {
		String xAxisTitle = list.get(0).get(0);
		for(int idx=0; idx<columnCount; idx++) {
			try {
				BoxChart chart = new BoxChartBuilder().width(chartWidth).height(chartHeight).theme(Styler.ChartTheme.GGPlot2).title(title).build();

				chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
				chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
				chart.getStyler().setHasAnnotations(true);
				chart.getStyler().setBoxplotCalCulationMethod(BoxplotCalCulationMethod.N_LESS_1_PLUS_1);

				int startIndex = 0;
				int xDataArrLength = columnCount;
				if(!isOneX) {
					startIndex = 1;
					xDataArrLength++;
				}
				String[] xDataArr = new String[xDataArrLength];
				for(int i=startIndex; i<xDataArrLength; i++) {
					if(isOneX) xDataArr[i] = list.get(0).get(i);
					else xDataArr[i-1] = list.get(0).get(i);
				}
				List<Number[]> yDataList = (List<Number[]>) map.get("yDataList");
				chart.addSeries(xDataArr[idx], Arrays.asList(yDataList.get(idx)));
				BitmapEncoder.saveBitmapWithDPI(chart, this.path+chartImgName, BitmapEncoder.BitmapFormat.JPG, 300);
				doc.createParagraph().createRun().addPicture(new FileInputStream(this.path+chartImgName), XWPFDocument.PICTURE_TYPE_JPEG, chartImgName, Units.toEMU(insertChartWidth), Units.toEMU(insertChartHeight));
				new File(path+chartImgName).delete();
			}catch (Exception e){
				//e.printStackTrace();
			}
		}
		return doc;
	}

	/**
	 *
	 * @return
	 */
	public int getChartCount() {
		double[][] tempArr = new double[columnCount][rowCount];
		double[] avgArr = new double[columnCount];
		if(isOneX) {
			for(int i=0; i<columnCount; i++) {
				for(int j=0; j<rowCount; j++) {
					tempArr[i][j] = Double.parseDouble(list.get(j+1).get(i));
				}
			}
			for(int i=0; i<columnCount; i++) {
				avgArr[i] = 0;
				for(int j=0; j<rowCount; j++) {
					if(avgArr[i] == 0) {
						avgArr[i] += tempArr[i][j];
					}else {
						avgArr[i] = (avgArr[i]+tempArr[i][j])/2;
					}
				}
			}
			for(int i=0; i<columnCount-1; i++) {
				double diff = Math.abs(avgArr[i]-avgArr[i+1]);
				if(diff > Math.pow(avgArr[i], 2)) {
					return columnCount;
				}
			}
		}else {
			
		}
		return 1;
	}
	
	/**
	 * 차트에 들어갈 x축, y축 데이터를 정리하여 담음
	 */
	public void setXYData() {
		this.map = new HashMap<String, Object>();
		
		/* row가 하나뿐일 때 */
		if(isOneRow & !isOneColumn) {
			if(isOneX) { //x값이 하나 뿐일 때(첫번째 column 값이 숫자일 때)
				this.columnCount++;
				String[] xDataArr = new String[columnCount];
				Number[] yDataArr = new Number[columnCount];
				for(int i=0; i<columnCount; i++) {
					xDataArr[i] = list.get(0).get(i);
					yDataArr[i] = Double.parseDouble(list.get(1).get(i).substring(0, list.get(1).get(i).length()-1));
				}
				this.map.put("xDataArr", xDataArr);
				this.map.put("yDataArr", yDataArr);
			}else { //x값이 여러개일 때(첫번째 column 값이 숫자가 아닐 때)
				String[] xDataArr = new String[columnCount];
				Number[] yDataArr = new Number[columnCount];
				for(int i=1; i<columnCount+1; i++) {
					yDataArr[i-1] = Double.parseDouble(list.get(1).get(i).substring(0, list.get(1).get(i).length()-1));
				}
				this.map.put("xDataArr", xDataArr);
				this.map.put("yDataArr", yDataArr);
			}
		}
		
		/* row와 column이 여러개일 때 */
		else if(!isOneRow & !isOneColumn) { 
			if(isOneX) { //x값이 하나 뿐일 때(첫번째 column 값이 숫자일 때)
				this.columnCount++;
				String[] xDataArr = new String[1];
				xDataArr[0] = " ";
				List<Number[]> yDataList = new ArrayList<>();
				Number[][] tempDataArr = new Number[rowCount][columnCount];
				for(int i=0; i<columnCount; i++) {
					for(int j=0; j<rowCount+1; j++) {
						if(j==0) continue;
						tempDataArr[j-1][i] = Double.parseDouble(list.get(j).get(i));
					}
				}
				//System.out.println(new ArrayList<String>(Arrays.asList(xDataArr)));
				for(int i=0; i<columnCount; i++) {
					Number[] yDataArr = new Number[rowCount];
					for(int j=0; j<rowCount; j++) {
						yDataArr[j] = tempDataArr[j][i];
					}
					//System.out.println(new ArrayList<Number>(Arrays.asList(yDataArr)));
					yDataList.add(yDataArr);
				}
				this.map.put("xDataArr", xDataArr);
				this.map.put("yDataList", yDataList);
			}else { //x값이 여러개일 때(첫번째 column 값이 숫자가 아닐 때)
				String[] xDataArr = new String[rowCount];
				List<Number[]> yDataList = new ArrayList<>();
				for(int i=0; i<rowCount+1; i++) {
					if(i==0) continue;
					xDataArr[i-1] = list.get(i).get(0);
				}
				Number[][] tempDataArr = new Number[rowCount][columnCount];
				for(int i=0; i<columnCount+1; i++) {
					for(int j=0; j<rowCount+1; j++) {
						if(i==0 || j==0) continue;
						tempDataArr[j-1][i-1] = Double.parseDouble(list.get(j).get(i));
					}
				}
				//System.out.println(new ArrayList<String>(Arrays.asList(xDataArr)));
				for(int i=0; i<columnCount; i++) {
					Number[] yDataArr = new Number[rowCount];
					for(int j=0; j<rowCount; j++) {
						yDataArr[j] = tempDataArr[j][i];
					}
					//System.out.println(new ArrayList<Number>(Arrays.asList(yDataArr)));
					yDataList.add(yDataArr);
				}
				this.map.put("xDataArr", xDataArr);
				this.map.put("yDataList", yDataList);
			}
		}
	}

	public static void main(String[] args) {
		ChartHelper chartHelper = new ChartHelper();
		Rule rule = new Rule();
		rule.setBottom_level_name("테스트 제목");
		rule.setResult("[[전사자, 데이터1, 데이터2, 데이터3, 데이터4, 데이터5], [이민지(als3o@naver.com), 10%, 15%, 25%, 35%, 15%]]");
		XWPFDocument doc = new XWPFDocument();
		String path = "/home/namu/Documents/test/report/docx/";
		doc = chartHelper.checkChartForm(doc, rule, path);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream("/home/namu/Documents/doc1.docx");
			doc.write(fileOutputStream);
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
