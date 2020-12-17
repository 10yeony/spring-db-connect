package kr.com.inspect.dto;

/**
 * 전사규칙 클래스 (rule_top_level, rule_middle_level, rule_bottom_level 테이블)
 * @author Yeonhee Kim
 *
 */
public class Rule {
	
	/**
	 * 게시글 번호
	 */
	private int row_num;
	
	/**
	 * 대분류 primary key(auto increment)
	 */
	private int top_level_id;
	
	/**
	 * 대분류 이름(회사명)
	 */
	private String top_level_name;
	
	/**
	 * 중분류 primary key(auto increment)
	 */
	private int middle_level_id;
	
	/**
	 * 중분류 이름(규칙 묶음)
	 */
	private String middle_level_name;
	
	/**
	 * 소분류 primary key(auto increment)
	 */
	private int bottom_level_id;
	
	/**
	 * 소분류 이름(디테일한 규칙명)
	 */
	private String bottom_level_name;
	
	/**
	 * 규칙에 대한 설명
	 */
	private String description;
	
	/**
	 * 규칙과 관련한 코드의 자바 파일명
	 */
	private String file_name;
	
	/**
	 * 규칙 작성자(회원 아이디)
	 */
	private String creator;
	
	/**
	 * 규칙과 관련한 코드 내용<br>
	 * DB에는 저장하지 않고, 프론트에서 받아와서 이 내용으로 자바 파일 생성
	 */
	private String contents;
	
	/**
	 * 작성한 코드를 컴파일한 후 결과값
	 */
	private String result;
	
	

	public Rule(int row_num, int top_level_id, String top_level_name, int middle_level_id, String middle_level_name,
			int bottom_level_id, String bottom_level_name, String description, String file_name, String creator,
			String contents, String result) {
		super();
		this.row_num = row_num;
		this.top_level_id = top_level_id;
		this.top_level_name = top_level_name;
		this.middle_level_id = middle_level_id;
		this.middle_level_name = middle_level_name;
		this.bottom_level_id = bottom_level_id;
		this.bottom_level_name = bottom_level_name;
		this.description = description;
		this.file_name = file_name;
		this.creator = creator;
		this.contents = contents;
		this.result = result;
	}

	public Rule() {}

	public int getRow_num() {
		return row_num;
	}
	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}
	public int getTop_level_id() {
		return top_level_id;
	}
	public void setTop_level_id(int top_level_id) {
		this.top_level_id = top_level_id;
	}
	public String getTop_level_name() {
		return top_level_name;
	}
	public void setTop_level_name(String top_level_name) {
		this.top_level_name = top_level_name;
	}
	public int getMiddle_level_id() {
		return middle_level_id;
	}
	public void setMiddle_level_id(int middle_level_id) {
		this.middle_level_id = middle_level_id;
	}
	public String getMiddle_level_name() {
		return middle_level_name;
	}
	public void setMiddle_level_name(String middle_level_name) {
		this.middle_level_name = middle_level_name;
	}
	public int getBottom_level_id() {
		return bottom_level_id;
	}
	public void setBottom_level_id(int bottom_level_id) {
		this.bottom_level_id = bottom_level_id;
	}
	public String getBottom_level_name() {
		return bottom_level_name;
	}
	public void setBottom_level_name(String bottom_level_name) {
		this.bottom_level_name = bottom_level_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Rule [row_num=" + row_num + ", top_level_id=" + top_level_id + ", top_level_name=" + top_level_name
				+ ", middle_level_id=" + middle_level_id + ", middle_level_name=" + middle_level_name
				+ ", bottom_level_id=" + bottom_level_id + ", bottom_level_name=" + bottom_level_name + ", description="
				+ description + ", file_name=" + file_name + ", creator=" + creator + ", contents=" + contents
				+ ", result=" + result + "]";
	}
	

}
