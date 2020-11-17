package kr.com.inspect.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.com.inspect.dto.Member;

public interface MemberService extends UserDetailsService {
	/* 회원가입 */
	public int registerMember(Member member); 
	
	/* 회원정보를 가져옴 */
	public Member readMemberById(String member_id); 
	
	/* 아이디 중복 체크 */
	public int idCheck(String member_id);
	
	/* 회원 정보 수정 */
	public int updateMember(Member member);
	
	/* 회원 탈퇴 */
	public int deleteMember(String member_id);
	
	/* Spring Security에서 아이디로 회원 정보를 읽어옴 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/* 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함 */
	public Collection<GrantedAuthority> getAuthorities(String username);
	
	/* 사용할 PasswordEncoder를 리턴해줌 */
	public PasswordEncoder passwordEncoder();
}
