package kr.com.inspect.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

import kr.com.inspect.dto.Rule;
import kr.com.inspect.util.CompressZip;
import kr.com.inspect.util.FileManager;
import kr.com.inspect.util.UnZip;

/**
 * 룰 실행결과를 압축하거나 압축해제하는 객체
 * @author Yeonhee Kim
 * @version 1.0
 */
public class PrevRuleResult {
	/**
	 * 압축한 zip 파일을 보관하는 경로
	 */
	private String compressZipPath;
	
	/**
	 * 압축해제한 디렉토리를 보관하는 경로
	 */
	private String unZipPath;
	
	/**
	 * 파일 또는 폴더를 처리할 객체
	 */
	private FileManager fileManager;
	
	/**
	 * 경로와 파일 및 폴더 처리 객체를 할당하는 생성자
	 */
	public PrevRuleResult() {
        String resource = "properties" + File.separator +"directory.properties";
        Properties properties = new Properties();
        try{
            Reader reader = Resources.getResourceAsReader(resource);
            properties.load(reader);
            this.compressZipPath = properties.getProperty("rule.prevResult.compressZip.directory");
            this.unZipPath = properties.getProperty("rule.prevResult.unZip.directory");
        }catch (Exception e){
            e.printStackTrace();
        }
		this.fileManager = new FileManager();
	}
	
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
			writer.write(rule.getResult()+"\n");
			if(rule.getVersion() != null) {
				writer.write(rule.getVersion());
			}
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
	 * @param bottom_level_id_arr 소분류 id 배열
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
							case 5 :
								rule.setVersion(line);
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
}
