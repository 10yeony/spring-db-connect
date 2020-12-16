package kr.com.inspect.dto;

/**
 * 전사규칙 클래스 (rule_top_level, rule_middle_level, rule_bottom_level 테이블)
 * @author Yeonhee Kim
 *
 */
public class CustomRule {
	
	/**
	 * 규칙과 관련한 코드의 자바 파일명
	 */
	private String file_name;
	
	/**
	 * 규칙 작성자(회원 아이디)
	 */
	private String creator;
	
	public CustomRule() {}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "CustomRule [file_name=" + file_name + ", creator=" + creator + "]";
	}

	public CustomRule(String file_name, String creator) {
		super();
		this.file_name = file_name;
		this.creator = creator;
	}
	
	
}
