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
		
		/* 값이 숫자로만 되어있고 데이터의 개수가 너무 많지 않은 경우 */
		else if(!containsChar && rowCount <= 10) {
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
				doc = drawBarChart(doc);
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
		try {
			String xAxisTitle = "";
			if(!isOneX) xAxisTitle = list.get(0).get(0);
			CategoryChart chart = new CategoryChartBuilder().width(chartWidth).height(chartHeight).theme(Styler.ChartTheme.GGPlot2).title(title).xAxisTitle(xAxisTitle).yAxisTitle("").build();
			
			chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW); //속성 상자 위치
			chart.getStyler().setAvailableSpaceFill(.96); //막대 간 간격
//			chart.getStyler().setOverlapped(true); //막대 겹치게 
			chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE)); // 차트 배경색
			chart.getStyler().setHasAnnotations(true);
			
			String[] xDataArr = (String[]) map.get("xDataArr");
			List<Number[]> yDataList = (List<Number[]>) map.get("yDataList");
			for(int i=0; i<yDataList.size(); i++) {
				if(isOneX) chart.addSeries(list.get(0).get(i), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(i))));
				else chart.addSeries(list.get(0).get(i+1), new ArrayList<String>(Arrays.asList(xDataArr)), new ArrayList<Number>(Arrays.asList(yDataList.get(i))));
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
	 * 차트에 들어갈 x축, y축 데이터를 정리하여 담음
	 * @param tableType 테이블 유형
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
					xDataArr[i-1] = list.get(0).get(i);
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
	
//	public static void main(String[] args) {
//		ChartHelper chartHelper = new ChartHelper();
//		Rule rule = new Rule();
//		rule.setBottom_level_name("테스트 제목");
//		//rule.setResult("[[전사자, 데이터1, 데이터2, 데이터3, 데이터4, 데이터5], [이민지(als3o@naver.com), 10%, 15%, 25%, 35%, 15%]]");
//		//rule.setResult("[[전사자, 맞음, 틀림], [이민지(als3o@naver.com), 맞는 내용, 틀린 내용]]");
//		//rule.setResult("[[학년, 여 (단위: 분), 남 (단위: 분), 미기입 (단위: 분)], [1학년, 4313.54, 1678.85, 1629.71], [2학년, 2427.36, 2141.98, 1453.73], [3학년, 1557.38, 6862.85, 2519.35]]");
//		//rule.setResult("[[이중전사의 형태를 잘못 입력한 문장, 이중전사 형태에 문제가 없는 문장], [30%, 70%]]");
//		//rule.setResult("[[아스키코드 이외의 문자가 포함된 문장 수, 전체 문장수], [14, 25]]");
//		//rule.setResult("[[전사자, 이중전사의 형태를 잘못 입력한 문장수, 입력한 총 문장수], [나성진(5878997@naver.com), 9, 2240], [이민지(als3o@naver.com), 16, 4857], [목가현(alsdudtnl@naver.com), 17, 2425], [김희경(banila778@gmail.com), 17, 2370], [이정민(betty2793@naver.com), 20, 6525], [김우진(biff4933@gmail.com), 16, 902], [김경문(brightsome_s@naver.com), 10, 1723], [성종호(cameata@naver.com), 16, 4954], [조민서(ccmmss0902@naver.com), 39, 3572], [채창완(changwanei@gmail.com), 16, 6538], [최고은(choigoeun93@naver.com), 14, 2026], [조원기(chowk1109@naver.com), 127, 6005], [이철희(cjfgml2358@naver.com), 14, 2005], [홍철(cjfl2564@naver.com), 23, 2932], [이찬호(cksgh1211@naver.com), 14, 3181], [김수현(clousy23@gmail.com), 45, 2129], [안세운(cqrita@naver.com), 2, 995], [권다혜(dahkwon@gmail.com), 4, 881], [신동은(de0309@naver.com), 3, 9991], [최소윤(developer.soyun@gmail.com), 6, 9532], [우동헌(dev.udong.top@gmail.com), 6, 4613], [최현호(disc0803@naver.com), 7, 671], [이주희(dusgkrwkd@naver.com), 13, 5821], [나민아(dvl.namina@gmail.com), 9, 2684], [노다현(ekgusgn@naver.com), 11, 6301], [최원진(emdmel@naver.com), 43, 1767], [배근호(Engineer.BGH@gmail.com), 47, 2398], [오인영(goohiny@gmail.com), 15, 5277], [김현아(hakim1004@naver.com ), 9, 1432], [조주연(hcjy97@naver.com), 24, 6232], [김다영(hoonlee7878@gmail.com), 8, 4243], [박찬석(ict.cspark@gmail.com), 22, 4074], [이재상(jake0403@naver.com), 31, 5187], [정화윤(jeonghwayun0119@naver.com), 23, 1319], [이지민(jimin778@gmail.com), 11, 1223], [강지영(jiyoung5724@naver.com ), 14, 6359], [이지은(jk04115@naver.com), 33, 4557], [김지혜(jkl4887@naver.com), 11, 1923], [박종태(joupo6656@naver.com), 16, 1352], [권정남(kamu6667@gmail.com), 1, 1319], [이노진(laranja14@naver.com), 4, 1845], [김지민(lkw3456@naver.com), 23, 2159], [이장현(long-flash@daum.net), 1, 431], [이서현(lsh960317@naver.com), 1, 1079], [박용석(machinedragon95@gmail.com), 106, 1216], [안성민(min110305@naver.com), 5, 4588], [정지원(mnvampire@naver.com), 15, 1378], [문다정(moondj82@naver.com), 5, 4970], [문영웅(moonoo3@naver.com), 38, 3264], [김정욱(mybiggold@naver.com), 77, 2936], [김영인(oo1029oo@gmail.com), 13, 3670], [오승철(osc2548@naver.com), 55, 3538], [김은규(pang0777@naver.com), 28, 6983], [명상하(papayatt@naver.com), 144, 5689], [박범진(qjawlsqjacks@naver.com ), 22, 1766], [이유빈(qkekdml19@naver.com), 6, 871], [박누리(qkrsnrl@naver.com), 31, 951], [박지윤(qkrwldbs0408@gmail.com), 1, 3347], [한겨레(ray56260201@gmail.com), 41, 2911], [맹주석(re95@naver.com), 21, 3861], [조준형(reqip95@naver.com), 7, 2087], [김찬휘(rlacksgnl007@naver.com), 3, 1529], [권보규(rnjsqhrbsla@naver.com), 2, 1221], [이용희(sealwh@naver.com), 10, 1968], [석재호(seok7115@naver.com), 25, 4795], [김성민(seong_min829@naver.com), 36, 5276], [최휘원(shine199605@naver.com), 20, 5456], [김동우(SmartFDW@gmail.com), 15, 1831], [윤소윤(soyun3963@gmail.com), 15, 2740], [정준영(suj710101@gmail.com), 87, 8494], [김효원(sun4131@gmail.com), 26, 4767], [손동기(thsehdrl2@gmail.com), 23, 2410], [백승우(tight729@naver.com ), 1, 1060], [김성은(tjddms0617@naver.com), 6, 7801], [송윤석(tjr0418@naver.com), 11, 3685], [김택완(twan4220@gmail.com), 28, 1261], [김현수(wishinghyun@naver.com), 26, 6369], [조창엽(wliod202@naver.com), 33, 4508], [윤현식(yh7335@naver.com), 19, 6556], [윤희영(yhy0818@gmail.com), 18, 929], [윤무영(ymy2690@naver.com), 30, 3478], [유지수(yoohorn1@naver.com), 10, 1896], [이정윤(yyyljy@gmail.com), 5, 1303], [박재현(z8824@naver.com), 26, 3261]]");
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
