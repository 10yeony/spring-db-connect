package kr.com.inspect.util;

import java.io.File;

/**
 * 파일 및 폴더와 관련된 처리를 하는 유틸리티
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
public class FileManager {
	
	/**
	 * 파일을 삭제함
	 * @param path 파일 경로
	 */
	public void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				// System.out.println("파일 삭제 성공");
			}else {
				// System.out.println("파일 삭제 실패");
			}
		}else {
			// System.out.println("파일이 존재하지 않습니다.");
		}
	}
	
	/**
	 * 폴더를 삭제함
	 * @param path 폴더 경로
	 */
	public void deleteFolder(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
				File[] folderList = folder.listFiles(); 
				for (int i = 0; i < folderList.length; i++) {
					if(folderList[i].isFile()) {
						folderList[i].delete();
						//System.out.println("파일이 삭제되었습니다.");
					}else {
						deleteFolder(folderList[i].getPath()); 
						//System.out.println("폴더가 삭제되었습니다.");
					}
					folderList[i].delete();
				}
				folder.delete(); 
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
