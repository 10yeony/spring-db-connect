package kr.com.inspect.dto;

/* User 테이블(회원 로그인, 회원가입 관련) */
public class User {
	private String userid; // 사용자 아이디
	private String pwd; // 비밀번호
	private boolean useCookie; // 쿠키 사용

	public boolean isUseCookie() {
		return useCookie;
	}

	public void setUseCookie(boolean useCookie) {
		this.useCookie = useCookie;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public User(String userid, String pwd) {
		super();
		this.userid = userid;
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", pwd=" + pwd + "]";
	}

}
