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

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

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
	 * 회원 정보를 모두 가지고 옴
	 * @return 회원 목록
	 */
	public List<Member> getMemberList();
	
	/**
	 * 권한명으로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @return 해당 권한을 가진 회원 목록
	 */
	public List<Member> getMemberListUsingRole(String role);
	
	/**
	 * 회원 정보 수정
	 * @param session
	 * @param member
	 * @return member 테이블에 수정된 row의 수
	 */
	public int updateMember(HttpSession session, Member member);
	
	/**
	 * 관리자 권한으로 회원 정보(권한) 수정
	 * @param member_id
	 * @param authoritiesArr
	 * @return authority 테이블에 수정된 row의 수
	 */
	public int updateAuthorities(String member_id, String[] authoritiesArr);
	
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
	 */
	public void deleteMember(String member_id);
	
	/**
	 * Spring Security에서 아이디로 회원 정보를 읽어옴
	 * @param username 
	 * @exception UsernameNotFoundException 유저정보 예외처리
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
