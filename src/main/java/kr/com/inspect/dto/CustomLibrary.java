package kr.com.inspect.dto;

/**
 * 사용자가 import하고자 등록한 커스텀 라이브러리
 * @author Myeongjun Hwang
 * @author Yeonhee Kim
 *
 */
public class CustomLibrary {
	
	/**
	 * auto increment된 라이브러리 아이디
	 */
	private int id;
	
	/**
	 * 라이브러리 등록자
	 */
	private String creator;
	
	/**
	 * 라이브러리 파일명
	 */
	private String file_name;

	/**
	 * class 파일의 패키지명
	 */
	private String class_package;

	public CustomLibrary() {}
	public CustomLibrary(int id, String creator, String file_name, String class_package) {
		super();
		this.id = id;
		this.creator = creator;
		this.file_name = file_name;
		this.class_package = class_package;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getClass_package(){return class_package;}
	public void setClass_package(String class_package){this.class_package = class_package;}
	
	@Override
	public String toString() {
		return "CustomLibrary [id=" + id + ", creator=" + creator + ", file_name=" + file_name +", class_package=" + class_package + "]";
	}
}
