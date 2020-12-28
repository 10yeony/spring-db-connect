package kr.com.inspect.dto;

/**
 * Rule 관련 로그
 * @author Yeonhee Kim
 * @version 1.0
 */
public class RuleLog extends UsingLog {
	/**
	 * 대분류 아이디
	 */
	private int top_level_id;
	
	/**
	 * 대분류 이름
	 */
	private String top_level_name;

	/**
	 * 중분류 아이디
	 */
	private int middle_level_id;
	
	/**
	 * 중분류 이름
	 */
	private String middle_level_name;
	
	/**
	 * 소분류 아이디
	 */
	private int bottom_level_id;
	
	/**
	 * 소분류 이름
	 */
	private String bottom_level_name;
	
	/**
	 * 라이브러리 파일 이름
	 */
	private String library_file_name;
	
	/**
	 * 커스텀 라이브러리 파일이 클래스 파일일 경우 패키지명
	 */
	private String class_package;
	
	/**
	 * 사용 로그 번호(Foreign Key)
	 */
	private int using_log_no;

	public RuleLog() {}
	public RuleLog(int top_level_id, String top_level_name, int middle_level_id, String middle_level_name,
			int bottom_level_id, String bottom_level_name, String library_file_name, String class_package,
			int using_log_no) {
		super();
		this.top_level_id = top_level_id;
		this.top_level_name = top_level_name;
		this.middle_level_id = middle_level_id;
		this.middle_level_name = middle_level_name;
		this.bottom_level_id = bottom_level_id;
		this.bottom_level_name = bottom_level_name;
		this.library_file_name = library_file_name;
		this.class_package = class_package;
		this.using_log_no = using_log_no;
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
	public String getLibrary_file_name() {
		return library_file_name;
	}
	public void setLibrary_file_name(String library_file_name) {
		this.library_file_name = library_file_name;
	}
	public String getClass_package() {
		return class_package;
	}
	public void setClass_package(String class_package) {
		this.class_package = class_package;
	}
	public int getUsing_log_no() {
		return using_log_no;
	}
	public void setUsing_log_no(int using_log_no) {
		this.using_log_no = using_log_no;
	}
	public void setRule(Rule rule) {
		setTop_level_id(rule.getTop_level_id());
		setTop_level_name(rule.getTop_level_name());
		setMiddle_level_id(rule.getMiddle_level_id());
		setMiddle_level_name(rule.getMiddle_level_name());
		setBottom_level_id(rule.getBottom_level_id());
		setBottom_level_name(rule.getBottom_level_name());
	}
	public void setCustomLibrary(CustomLibrary customLibrary) {
		setMember_id(customLibrary.getCreator());
		setLibrary_file_name(customLibrary.getFile_name());
		setClass_package(customLibrary.getClass_package());
	}
	
	@Override
	public String toString() {
		return "RuleLog [top_level_id=" + top_level_id + ", top_level_name=" + top_level_name + ", middle_level_id="
				+ middle_level_id + ", middle_level_name=" + middle_level_name + ", bottom_level_id=" + bottom_level_id
				+ ", bottom_level_name=" + bottom_level_name + ", library_file_name=" + library_file_name
				+ ", class_package=" + class_package + ", using_log_no=" + using_log_no + "]";
	}
}
