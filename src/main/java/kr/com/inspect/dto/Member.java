package kr.com.inspect.dto;

/* Member 테이블(회원 로그인, 회원가입 관련) */
public class Member {
	private String member_id; // 사용자 아이디
	private String pwd; // 비밀번호
	private boolean useCookie; // 쿠키 사용
	
	public Member() {}
	public Member(String member_id, String pwd, boolean useCookie) {
		super();
		this.member_id = member_id;
		this.pwd = pwd;
		this.useCookie = useCookie;
	}
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean isUseCookie() {
		return useCookie;
	}
	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}
	
	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", pwd=" + pwd + ", useCookie=" + useCookie + "]";
	}
}
