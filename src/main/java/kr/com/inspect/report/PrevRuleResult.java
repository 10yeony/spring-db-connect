package kr.com.inspect.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import kr.com.inspect.dto.Rule;
import kr.com.inspect.util.CompressZip;
import kr.com.inspect.util.FileManager;
import kr.com.inspect.util.UnZip;

public class PrevRuleResult {
	private String compressZipPath = "/home/namu/Documents/test/rule/prevResult/compressZip/";
	private String unZipPath = "/home/namu/Documents/test/rule/prevResult/unZip/";
	
	/**
	 * 룰 실행 결과를 텍스트 파일로 쓰기
	 * @param rule 룰 소분류 아이디와 룰 실행 결과가 담긴 룰 객체
	 * @param folderName 폴더 이름(실행 시간)
	 */
	public void writeRuleResultTxtFile(Rule rule, String folderName) {
		String path = compressZipPath + folderName + File.separator;
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		File file = new File(path+rule.getBottom_level_id()+".txt");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(rule.getResult());
			writer.close();
		} catch (IOException e) {
			//e.printStackTrace();
		} 
	}
	
	/**
	 * 해당되는 폴더를 zip 파일로 압축함
	 * @param folderName 폴더명
	 */
	public void compressZip(String folderName) {
		CompressZip compressZip = new CompressZip(); 
		try { 
			if (!compressZip.compress((compressZipPath+folderName), compressZipPath, folderName)) { 
				//System.out.println("압축 실패");
			} 
		} catch (Throwable e) { 
			//e.printStackTrace(); 
		} finally {
			FileManager fileManager = new FileManager();
			fileManager.deleteFolder(compressZipPath+folderName);
		}
	}
	
	/**
	 * zip 파일 압축을 해제함
	 * @param zipFileName zip 파일 이름
	 */
	public void unZip(String zipFileName) {
		UnZip unZip = new UnZip();
		if (!unZip.unZip(compressZipPath, zipFileName, unZipPath)) { 
			//System.out.println("압축 해제 실패"); 
		}
	}
	
	
	
	public static void main(String[] args) {
		/* 룰 실행 결과 파일 쓰기 */
		Rule rule = new Rule();
		rule.setBottom_level_id(22);
		rule.setResult("[[전사자, 아스키코드 이외의 기호를 사용한 문장수, 입력한 총 문장수, 잘못된 문장 비율], [이민지(als3o@naver.com), 1, 4857, 0.02%], [김희경(banila778@gmail.com), 1, 2370, 0.04%], [김우진(biff4933@gmail.com), 2, 902, 0.22%], [조원기(chowk1109@naver.com), 1, 6005, 0.02%], [홍철(cjfl2564@naver.com), 1, 2932, 0.03%], [김수현(clousy23@gmail.com), 1, 2129, 0.05%], [박찬석(ict.cspark@gmail.com), 1, 4074, 0.02%], [문영웅(moonoo3@naver.com), 1, 3264, 0.03%], [정준영(suj710101@gmail.com), 2, 8494, 0.02%], [김효원(sun4131@gmail.com), 3, 4767, 0.06%]]");
		PrevRuleResult prevRuleResult = new PrevRuleResult();
		String TIME = prevRuleResult.getTime();
		prevRuleResult.writeRuleResultTxtFile(rule, TIME);
		
		rule.setBottom_level_id(23);
		prevRuleResult.writeRuleResultTxtFile(rule, TIME);
		
		/* 압축 */
		prevRuleResult.compressZip(TIME);
		
		/* 압축해제 */
		prevRuleResult.unZip(TIME);
	}
	
	public String getTime() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(zone);
		String currentTime = format.format(System.currentTimeMillis());
		return currentTime;
	}
}
