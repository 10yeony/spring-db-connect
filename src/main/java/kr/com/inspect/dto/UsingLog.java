package kr.com.inspect.dto;

/**
 * 사용자의 사용 로그를 기록함
 * @author Yeonhee Kim
 * @version 1.0
 */
public class UsingLog {
	/**
	 * 페이징 처리시 필요한 게시글 번호
	 */
	private int row_num;
	
	/**
	 * 순번 (primary key이자 auto increment)
	 */
	private int no;
	
	/**
	 * ip 주소
	 */
	private String ip_addr;
	
	/**
	 * 접속 시간
	 */
	private String time;
	
	/**
	 * 내용
	 */
	private String content;
	
	/**
	 * 회원 아이디(foreign key)
	 */
	private String member_id;

	public UsingLog() {}
	public UsingLog(int row_num, int no, String ip_addr, String time, String content, String member_id) {
		super();
		this.row_num = row_num;
		this.no = no;
		this.ip_addr = ip_addr;
		this.time = time;
		this.content = content;
		this.member_id = member_id;
	}

	public int getRow_num() {
		return row_num;
	}
	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getIp_addr() {
		return ip_addr;
	}
	public void setIp_addr(String ip_addr) {
		this.ip_addr = ip_addr;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	@Override
	public String toString() {
		return "UsingLog [row_num=" + row_num + ", no=" + no + ", ip_addr=" + ip_addr + ", time=" + time + ", content="
				+ content + ", member_id=" + member_id + "]";
	}
}
