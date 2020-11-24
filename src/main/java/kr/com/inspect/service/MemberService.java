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
 * 회원정보 Service Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface MemberService extends UserDetailsService {
	
	/**
	 * 회원가입
	 * @param member 회원 정보
	 * @return 회원정보값을 리턴
	 */
	public int registerMember(Member member); 
	
	/**
	 * 아이디로 회원정보를 가져옴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값을 리턴
	 */
	public Member readMemberById(String member_id); 
	
	/**
	 * 회원가입시 해당 요소가 DB에 존재하는지 중복 체크 
	 * @param object DB 객체
	 * @param value 여부 확인
	 * @return DB에 존재하는 해당 요소의 총 row 수를 리턴
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
	 * @param session 해당 유저의 세션
	 * @param member 회원 정보
	 * @return member 테이블에 수정된 row의 수
	 */
	public int updateMember(HttpSession session, Member member);
	
	/**
	 * 관리자 권한으로 회원 정보(권한) 수정
	 * @param member_id 회원 아이디
	 * @param authoritiesArr 권한 배열
	 * @return authority 테이블에 수정된 row의 수
	 */
	public int updateAuthorities(String member_id, String[] authoritiesArr);
	
	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀 번호
	 * @return member 테이블에 수정된 row의 수
	 */
	public int updatePwd(String member_id, String pwd);
	
	/**
	 * 비밀번호를 잊어버린 회원에게 아이디와 이메일을 받고 임시 비밀번호를 메일로 발송 
	 * @param member_id 회원 아이디
	 * @param email 회원 이메일
	 * @return 
	 */
	public String sendPwdToEmail(String member_id, String email);
	
	/**
	 * 회원 탈퇴 
	 * @param member_id 회원 아이디
	 */
	public void deleteMember(String member_id);
	
	/**
	 * Spring Security에서 아이디로 회원 정보를 읽어옴
	 * @param username 회원 이름
	 * @exception UsernameNotFoundException 유저정보 예외처리
	 * @return 회원 정보
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/**
	 * 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함
	 * @param username 회원 이름
	 * @return 권한 목록
	 */
	public Collection<GrantedAuthority> getAuthorities(String username);
	
	/**
	 * 사용할 PasswordEncoder를 리턴해줌
	 * @return 비밀번호 암호화 객체
	 */
	public PasswordEncoder passwordEncoder();
}
