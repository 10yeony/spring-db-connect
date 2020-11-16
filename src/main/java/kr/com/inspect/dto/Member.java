package kr.com.inspect.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* Member 테이블(회원 로그인, 회원가입 관련) */
public class Member implements UserDetails {
	
	/* 아이디 및 비밀번호 */
	private String member_id; // DB상의 사용자 아이디 컬럼명. 스프링 시큐리티의 username에 해당함.
	private String pwd; // DB상의 비밀번호 컬럼명. 스프링 시큐리티의 password에 해당함.
	
	/* 계정 관련 점검 */
	private boolean isAccountNonExpired; //계정이 만료되지 않았는지를 리턴
	private boolean isAccountNonLocked; //계정이 잠겨있지 않은지를 리턴
	private boolean isCredentialsNonExpired; //계정의 패스워드가 만료되지 않았는지를 리턴
	private boolean isEnabled; //계정이 사용가능한 계정인지를 리턴
	
	/* 권한 목록 */
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
	
	/* MyBatis 관련 DB 상에 아이디와 비밀번호를 넣고 가져옴 */
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
	
	/* 스프링 시큐리티 관련 아이디와 비밀번호를 가져옴 */
	@Override
	public String getUsername() {
		return getMember_id();
	}
	@Override
	public String getPassword() {
		return getPwd();
	}
	
	/* 계정 관련 점검 setter/getter */
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
	
	/* 권한 목록 setter/getter */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	@Override
	public String toString() {
		return "Member [member_id(username)=" + member_id + ", pwd(password)=" + pwd + ", isAccountNonExpired=" + isAccountNonExpired
				+ ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired=" + isCredentialsNonExpired
				+ ", isEnabled=" + isEnabled + ", authorities=" + authorities + "]";
	}
}
