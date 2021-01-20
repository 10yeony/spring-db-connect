package kr.com.inspect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 파일로 압축하는 클래스
 * @author Yeonhee Kim
 *
 */
public class CompressZip { 
	
	/**
	 * 파일의 압축 성공 여부를 반환함
	 * @param path 압축할 폴더 경로 
	 * @param outputPath 압축파일들이 있는 경로
	 * @param outputFileName 압축파일명
	 * @return 파일 압축 성공 여부
	 * @throws Throwable
	 */
	public boolean compress(String path, String outputPath, String outputFileName) throws Throwable {
		boolean isSucceeded = false; 
		int pos = outputFileName.lastIndexOf(".") == -1 ? outputFileName.length() : outputFileName.lastIndexOf(".");  
		if (!outputFileName.substring(pos).equalsIgnoreCase(".zip")) { 
			outputFileName += ".zip"; 
		} 
		File file = new File(path); 
		if (!file.exists()) { 
			throw new Exception("Not File!"); 
		} 
		
		FileOutputStream fos = null; 
		ZipOutputStream zos = null; 
		try { 
			fos = new FileOutputStream(new File(outputPath + outputFileName)); 
			zos = new ZipOutputStream(fos); 
			searchDirectory(file, file.getPath(), zos); 
			isSucceeded = true; 
		} catch (Throwable e) { 
			throw e; 
		} finally { 
			if (zos != null) zos.close(); 
			if (fos != null) fos.close(); 
		} 
		return isSucceeded;
	}

	/**
	 * 디렉터리를 탐색하여 하위 파일과 폴더를 검색함
	 * @param file 현재 파일
	 * @param root 루트 경로
	 * @param zos zip 출력 스트림
	 * @throws Exception 예외
	 */
	private void searchDirectory(File file, String root, ZipOutputStream zos) throws Exception {
		if (file.isDirectory()) { //디렉터리일 경우 재탐색(재귀) 
			File[] files = file.listFiles(); 
			for (File f : files) { 
				searchDirectory(f, root, zos); 
			} 
		} 
		else { 
			try { 
				compressZip(file, root, zos); 
			} catch (Throwable e) { 
				//e.printStackTrace(); 
			} 
		}
	}

	/**
	 * zip 파일로 압축함
	 * @param file 압축할 파일
	 * @param root 루트가 될 경로
	 * @param zos zip 출력 스트림
	 * @throws Throwable
	 */
	private void compressZip(File file, String root, ZipOutputStream zos) throws Throwable {
		FileInputStream fis = null; 
		try { 
			String zipName = file.getPath().replace(root + File.separator, ""); 
			fis = new FileInputStream(file); 
			ZipEntry zipentry = new ZipEntry(zipName); 
			zos.putNextEntry(zipentry); 
			int length = (int) file.length(); 
			byte[] buffer = new byte[length]; 
			fis.read(buffer, 0, length); 
			zos.write(buffer, 0, length); 
			zos.closeEntry(); 
		} catch (Throwable e) { 
			throw e; 
		} finally { 
			if (fis != null) fis.close(); 
		}
	}
}



