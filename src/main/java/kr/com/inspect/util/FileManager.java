package kr.com.inspect.util;

import java.io.File;

public class FileManager {
	public void deleteFolder(String path) {
		File folder = new File(path);
		try {
			if(folder.exists()){
				File[] folderList = folder.listFiles(); 

				for (int i = 0; i < folderList.length; i++) {
					if(folderList[i].isFile()) {
						folderList[i].delete();
						System.out.println("파일이 삭제되었습니다.");
					}else {
						deleteFolder(folderList[i].getPath()); 
						System.out.println("폴더가 삭제되었습니다.");
					}
					folderList[i].delete();
				}
				
				folder.delete(); 
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
