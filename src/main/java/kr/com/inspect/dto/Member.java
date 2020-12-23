package kr.com.inspect.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Member 테이블(회원 로그인, 회원가입 관련)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public class Member implements UserDetails {
	/**
	 * 서로 다른 자바 컴파일러 구현체 사이에서도 동일한 serialVersionUID값을 얻기 위해서는 명시적으로 serialVersionUID값을 선언해야 하며 가능한 serialVersionUID을 private으로 선언해야 함
	 */
	private static final long serialVersionUID = 1L;
	
	/* 아이디 및 비밀번호 */
	
	/**
	 * DB상의 사용자 아이디 컬럼명. 스프링 시큐리티의 username에 해당함.
	 */
	private String member_id;
	
	/**
	 * DB상의 비밀번호 컬럼명. 스프링 시큐리티의 password에 해당함.
	 */
	private String pwd;
	
	/**
	 * 회원 이름
	 */
	private String name;
	
	
	
	/* 계정 관련 점검 */
	
	/**
	 * 계정이 만료되지 않았는지를 리턴
	 */
	private boolean isAccountNonExpired;
	
	/**
	 * 계정이 잠겨있지 않은지를 리턴
	 */
	private boolean isAccountNonLocked;
	
	/**
	 * 계정의 패스워드가 만료되지 않았는지를 리턴
	 */
	private boolean isCredentialsNonExpired;
	
	/**
	 * 계정이 사용가능한 계정인지를 리턴
	 */
	private boolean isEnabled;
	
	/**
	 * 가입일
	 */
	private String join_date;


	
	/* 연락처 및 이메일(문자발송 및 메일링 서비스) */
	
	/**
	 * 연락처 정보
	 */
	private String phone;
	
	/**
	 * 이메일 정보
	 */
	private String email;

	/**
	 * 승인 정보
	 */
	private String approval;
	
	
	
	/* 권한 목록 */
	
	/**
	 * 계정이 갖고 있는 권한 목록을 리턴
	 */
	private Collection<? extends GrantedAuthority> authorities;
	
	public Member() {}
	public Member(String member_id, String pwd, String name, boolean isAccountNonExpired, boolean isAccountNonLocked,
			boolean isCredentialsNonExpired, boolean isEnabled, String join_date, String phone, String email,
			String approval, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.member_id = member_id;
		this.pwd = pwd;
		this.name = name;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
		this.join_date = join_date;
		this.phone = phone;
		this.email = email;
		this.approval = approval;
		this.authorities = authorities;
	}

	/**
	 * MyBatis 관련 DB 상의 Id getter
	 * @return
	 */
	public String getMember_id() {
		return member_id;
	}
	
	/**
	 * MyBatis 관련 DB 상의 Id setter
	 * @param member_id
	 */
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	/**
	 * MyBatis 관련 DB 상의 pwd getter
	 * @return
	 */
	public String getPwd() {
		return pwd;
	}
	
	/**
	 * MyBatis 관련 DB 상의 pwd setter
	 * @param pwd
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * 스프링 시큐리티 관련 아이디를 가져옴
	 */
	@Override
	public String getUsername() {
		return getMember_id();
	}
	
	/**
	 * 스프링 시큐리티 관련 비밀번호를 가져옴
	 */
	@Override
	public String getPassword() {
		return getPwd();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	public String getJoin_date() {
		return join_date;
	}
	
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() { 
		return name; 
	}

	public void setName(String name) { 
		this.name = name; 

	public String getApproval() { 
		return approval; 
	}

	public void setApproval(String approval) { 
		this.approval = approval; 
	}
	
	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", pwd=" + pwd + ", name=" + name + ", isAccountNonExpired="
				+ isAccountNonExpired + ", isAccountNonLocked=" + isAccountNonLocked + ", isCredentialsNonExpired="
				+ isCredentialsNonExpired + ", isEnabled=" + isEnabled + ", join_date=" + join_date + ", phone=" + phone
				+ ", email=" + email + ", approval=" + approval + ", authorities=" + authorities + "]";
	}
}
