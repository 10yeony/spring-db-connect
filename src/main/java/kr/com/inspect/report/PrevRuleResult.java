package kr.com.inspect.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import kr.com.inspect.dto.Rule;
import kr.com.inspect.util.CompressZip;
import kr.com.inspect.util.FileManager;
import kr.com.inspect.util.UnZip;

public class PrevRuleResult {
	private String compressZipPath = "/home/namu/Documents/test/rule/prevResult/compressZip/";
	private String unZipPath = "/home/namu/Documents/test/rule/prevResult/unZip/";
	private FileManager fileManager = new FileManager();
	
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
			writer.write(rule.getTop_level_name()+"\n");
			writer.write(rule.getMiddle_level_name()+"\n");
			writer.write(rule.getBottom_level_name()+"\n");
			writer.write(rule.getDescription()+"\n");
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
			fileManager.deleteFolder(compressZipPath+folderName);
		}
	}
	
	/**
	 * zip 파일 압축을 해제하고 파일을 읽어서 룰 리스트를 반환함
	 * @param zipFileName zip 파일 이름
	 * @return 룰 리스트
	 */
	public List<Rule> unZip(String zipFileName, int[] bottom_level_id_arr) {
		List<Rule> ruleList = new ArrayList<>();
		UnZip unZip = new UnZip();
		if (unZip.unZip(compressZipPath, zipFileName, unZipPath)) {
			FileReader fr = null;
			BufferedReader br = null;
			for(int i=0; i<bottom_level_id_arr.length; i++) {
				File file = new File(unZipPath + zipFileName + File.separator + bottom_level_id_arr[i] + ".txt");
				if(file.exists()) {
					int bottom_level_id = Integer.parseInt(file.getName().substring(0, file.getName().lastIndexOf(".")));
					Rule rule = new Rule();
					rule.setBottom_level_id(bottom_level_id);
					try { 
						fr = new FileReader(file);
						br = new BufferedReader(fr);
						String line = "";
						int index = 0;
						while((line = br.readLine()) != null){
							switch(index) {
							case 0 :
								rule.setTop_level_name(line);
								break;
							case 1 :
								rule.setMiddle_level_name(line);
								break;
							case 2 :
								rule.setBottom_level_name(line);
								break;
							case 3 : 
								rule.setDescription(line);
								break;
							case 4 : 
								rule.setResult(line);
								break;
							}
							index++;
						}
					} catch(IOException e) {
						//e.printStackTrace();
					} finally {
						try {
							if(br != null) br.close();
							if(fr != null) fr.close();
						} catch (IOException e) {
							//e.printStackTrace();
						}
					}
					ruleList.add(rule);
				}
			}
		}
		fileManager.deleteFolder(unZipPath + zipFileName); //압축해제 폴더 삭제
		return ruleList;
	}

	public static void main(String[] args) {
		/* 룰 실행 결과 파일 쓰기 */
		Rule rule = new Rule();
		rule.setBottom_level_id(22);
		rule.setTop_level_name("대분류");
		rule.setMiddle_level_name("중분류");
		rule.setBottom_level_name("소분류");
		rule.setDescription("[한국어 음성 전사규칙 1.1.10 : 단일 발화의 음성 길이는 최대 20초를 넘지 않도록 한다.] 규칙을 전사자 별로 검사한다.");
		rule.setResult("[[전사자, 아스키코드 이외의 기호를 사용한 문장수, 입력한 총 문장수, 잘못된 문장 비율], [이민지(als3o@naver.com), 1, 4857, 0.02%], [김희경(banila778@gmail.com), 1, 2370, 0.04%], [김우진(biff4933@gmail.com), 2, 902, 0.22%], [조원기(chowk1109@naver.com), 1, 6005, 0.02%], [홍철(cjfl2564@naver.com), 1, 2932, 0.03%], [김수현(clousy23@gmail.com), 1, 2129, 0.05%], [박찬석(ict.cspark@gmail.com), 1, 4074, 0.02%], [문영웅(moonoo3@naver.com), 1, 3264, 0.03%], [정준영(suj710101@gmail.com), 2, 8494, 0.02%], [김효원(sun4131@gmail.com), 3, 4767, 0.06%]]");
		PrevRuleResult prevRuleResult = new PrevRuleResult();
		String TIME = prevRuleResult.getTime();
		prevRuleResult.writeRuleResultTxtFile(rule, TIME);
		
		rule.setBottom_level_id(23);
		prevRuleResult.writeRuleResultTxtFile(rule, TIME);
		
		/* 압축 */
		prevRuleResult.compressZip(TIME);
		
		/* 압축해제 */
		int[] bottom_level_id_arr = {22, 23};
		List<Rule> ruleList = prevRuleResult.unZip(TIME, bottom_level_id_arr);
	}
	
	public String getTime() {
		TimeZone zone = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(zone);
		String currentTime = format.format(System.currentTimeMillis());
		return currentTime;
	}
}
