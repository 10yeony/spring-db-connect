package kr.com.inspect.dto;

/**
 * Rule 관련 로그
 * @author Yeonhee Kim
 * @version 1.0
 */
public class RuleLog extends UsingLog {
	/**
	 * 대분류 이름
	 */
	private String top_level_name;
	
	/**
	 * 중분류 이름
	 */
	private String middle_level_name;
	
	/**
	 * 소분류 이름
	 */
	private String bottom_level_name;
	
	/**
	 * 라이브러리 파일 이름
	 */
	private String library_file_name;
	
	/**
	 * 사용 로그 번호(Foreign Key)
	 */
	private int using_log_no;

	public RuleLog() {}
	public RuleLog(int row_num, int no, String ip_addr, String time, 
			String content, String member_id, int using_log_no, String library_file_name,
			String top_level_name, String middle_level_name, String bottom_level_name) {
		super(row_num, no, ip_addr, time, content, member_id);
		this.using_log_no = using_log_no;
		this.library_file_name = library_file_name;
		this.top_level_name = top_level_name;
		this.middle_level_name = middle_level_name;
		this.bottom_level_name = bottom_level_name;
	}
	
	public String getTop_level_name() {
		return top_level_name;
	}
	public void setTop_level_name(String top_level_name) {
		this.top_level_name = top_level_name;
	}
	public String getMiddle_level_name() {
		return middle_level_name;
	}
	public void setMiddle_level_name(String middle_level_name) {
		this.middle_level_name = middle_level_name;
	}
	public String getBottom_level_name() {
		return bottom_level_name;
	}
	public void setBottom_level_name(String bottom_level_name) {
		this.bottom_level_name = bottom_level_name;
	}
	public int getUsing_log_no() {
		return using_log_no;
	}
	public void setUsing_log_no(int using_log_no) {
		this.using_log_no = using_log_no;
	}
	public String getLibrary_file_name() {
		return library_file_name;
	}
	public void setLibrary_file_name(String library_file_name) {
		this.library_file_name = library_file_name;
	}
	
	@Override
	public String toString() {
		return super.toString() + " & RuleLog [top_level_name=" + top_level_name + ", middle_level_name=" + middle_level_name
				+ ", bottom_level_name=" + bottom_level_name + ", library_file_name=" + library_file_name
				+ ", using_log_no=" + using_log_no + "]";
	}
}
