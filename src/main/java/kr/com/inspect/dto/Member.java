package kr.com.inspect.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/* Member 테이블(회원 로그인, 회원가입 관련) */
// UserDetails를 implements할 수도 있지만, username, password를 쓰지 않았으므로 여기서는 패스. 
public class Member {
	private String member_id; // DB상의 사용자 아이디 컬럼명. 스프링 시큐리티의 username에 해당함.
	private String pwd; // DB상의 비밀번호 컬럼명. 스프링 시큐리티의 password에 해당함.
	private boolean isAccountNonExpired; //계정이 만료되지 않았는지를 리턴
	private boolean isAccountNonLocked; //계정이 잠겨있지 않은지를 리턴
	private boolean isCredentialsNonExpired; //계정의 패스워드가 만료되지 않았는지를 리턴
	private boolean isEnabled; //계정이 사용가능한 계정인지를 리턴
	private Collection<? extends GrantedAuthority> authorities; //계정이 갖고 있는 권한 목록을 리턴
	
	public Member() {}
	public Member(String member_id, String pwd, boolean isAccountNonExpired, boolean isAccountNonLocked,
			boolean isCredentialsNonExpired, boolean isEnabled, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.member_id = member_id;
		this.pwd = pwd;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
		this.authorities = authorities;
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
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", pwd=" + pwd + ", isAccountNonExpired=" + isAccountNonExpired
				+ ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired=" + isCredentialsNonExpired
				+ ", isEnabled=" + isEnabled + ", authorities=" + authorities + "]";
	}
}
