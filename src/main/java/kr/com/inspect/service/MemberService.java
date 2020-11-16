package kr.com.inspect.service;

import java.util.Collection;

import kr.com.inspect.dto.Member;

//UserDetailsService를 implements할 수도 있지만, username, password를 쓰지 않았으므로 여기서는 패스. 
public interface MemberService {
	/* 회원가입 */
	public  int registerMember(Member member); 
	
	/* 로그인 */
	public Member loginMember(Member member); 
	
	/* 아이디 중복 체크 */
	public int idCheck(String member_id);
	
	/* Spring Security에서 아이디로 회원 정보를 읽어옴 */
//	public Member loadMemberById(String member_id) throws Exception;
	
	/* 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함 */
	//public Collection<GrantedAuthority> getAuthorities(String username);
}
