package kr.com.inspect.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.com.inspect.dto.User;

public interface UserService extends UserDetailsService {
	/* 회원가입 */
	public  int registerUser(User user); 
	
	/* 로그인 */
	public User loginUser(User user); 
	
	/* 아이디 중복 체크 */
	public int idCheck(String username);
	
	/* Spring Security에서 아이디로 회원 정보를 읽어옴 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	/* 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함 */
	public Collection<GrantedAuthority> getAuthorities(String username);
}
