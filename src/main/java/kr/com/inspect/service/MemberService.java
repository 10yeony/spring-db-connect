package kr.com.inspect.service;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.com.inspect.dto.Member;

public interface MemberService extends UserDetailsService {
	
	/**
	 * 회원가입
	 * @param member
	 * @return
	 */
	public int registerMember(Member member); 
	
	/**
	 * 아이디로 회원정보를 가져옴
	 * @param member_id
	 * @return
	 */
	public Member readMemberById(String member_id); 
	
	/**
	 * 회원가입시 해당 요소가 DB에 존재하는지 중복 체크 
	 * @param object
	 * @param value
	 * @return
	 */
	public int registerCheck(String object, String value);

	/**
	 * 회원 정보 모두 가지고옴
	 * @return
	 */
	public List<Member> getMember();
	
	/**
	 * 회원 정보 수정
	 * @param session
	 * @param member
	 * @return
	 */
	public int updateMember(HttpSession session, Member member);

	/**
	 * 비밀번호 변경
	 * @param member_id
	 * @param pwd
	 * @return
	 */
	public int updatePwd(String member_id, String pwd);
	
	/**
	 * 회원 탈퇴 
	 * @param member_id
	 * @return
	 */
	public int deleteMember(String member_id);
	
	/**
	 * Spring Security에서 아이디로 회원 정보를 읽어옴
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/**
	 * 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함
	 * @param username
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(String username);
	
	/**
	 * 사용할 PasswordEncoder를 리턴해줌
	 * @return
	 */
	public PasswordEncoder passwordEncoder();
}
