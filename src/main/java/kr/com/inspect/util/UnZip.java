package kr.com.inspect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * zip 파일을 압축 해제하는 클래스
 * @author Yeonhee Kim
 * @version 1.0
 */
public class UnZip {
	/**
	 * 파일의 압축해제 성공 여부를 반환함
	 * @param zipPath 압축파일이 있는 경로
	 * @param zipFileName 압축파일 이름
	 * @param zipUnzipPath 압축을 풀 폴더
	 * @return 파일의 압축해제 성공 여부
	 */
	public boolean unZip(String zipPath, String zipFileName, String zipUnzipPath) {
		boolean isSucceeded = false; 
		zipUnzipPath = zipUnzipPath + zipFileName; 
		File zipFile = new File(zipPath + zipFileName + ".zip"); 
		FileInputStream fis = null; 
		ZipInputStream zis = null; 
		ZipEntry zipentry = null; 

		try { 
			if (makeFolder(zipUnzipPath)) { 
				//System.out.println("폴더를 생성했습니다"); 
			} 
			fis = new FileInputStream(zipFile); 
			zis = new ZipInputStream(fis, Charset.forName("EUC-KR")); 

			/* 압축되어 있는 ZIP 파일의 목록 조회 */ 
			while ((zipentry = zis.getNextEntry()) != null) { 
				String filename = zipentry.getName();
				File file = new File(zipUnzipPath, filename); 
				
				if (zipentry.isDirectory()) { //zipentry가 디렉터리일 경우
					file.mkdirs(); 
				} else { //zipentry가 파일일 경우
					try { 
						createFile(file, zis); 
					} catch (Throwable e) { 
						//e.printStackTrace(); 
					} 
				}
			}
			isSucceeded = true; 
		} catch (Exception e) { 
			isSucceeded = false; 
		} finally { 
			if (zis != null) { 
				try { 
					zis.close(); 
				} catch (IOException e) {
					//e.printStackTrace();
				} 
			} 
			if (fis != null) { 
				try { 
					fis.close(); 
				} catch (IOException e) {
					//e.printStackTrace();
				} 
			} 
		} 
		return isSucceeded;
	}
	
	/**
	 * zip 파일로부터 폴더를 만듦
	 * @param folder 생성할 폴더 경로와 이름
	 * @return 폴더 생성 성공 여부
	 */
	private boolean makeFolder(String folder) { 
		if (folder.length() < 0) { 
			return false; 
		} 
		String path = folder; 
		File Folder = new File(path); 
		if (!Folder.exists()) { 
			try { 
				Folder.mkdir(); 
			} catch (Exception e) { 
				//e.getStackTrace(); 
			} 
		} 
		return true; 
	}
	
	/**
	 * zip 파일로부터 파일을 만듦
	 * @param file 파일
	 * @param zis zip 입력 스트림
	 * @throws Throwable
	 */
	private void createFile(File file, ZipInputStream zis) throws Throwable { 
		File parentDir = new File(file.getParent()); 
		if (!parentDir.exists()) { 
			parentDir.mkdirs(); 
		} 
		FileOutputStream fos = null; 		
		try { 
			fos = new FileOutputStream(file); 
			byte[] buffer = new byte[256]; 
			int size = 0; 
			while ((size = zis.read(buffer)) > 0) { 
				fos.write(buffer, 0, size); 
			} 
		} catch (Throwable e) { 
			throw e; 
		} finally { 
			try {
				if(fos != null) fos.close(); 
			}catch(IOException e) {
				//e.printStackTrace();
			}
		}
	}
}

