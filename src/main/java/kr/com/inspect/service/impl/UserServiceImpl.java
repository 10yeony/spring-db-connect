package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.UserDao;
import kr.com.inspect.dto.User;
import kr.com.inspect.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	/* 회원가입 */
	@Override
	public int registerUser(User user) {
		return userDao.registerUser(user);
	}
	
	/* 로그인 */
	@Override
	public User loginUser(User user) {
		return userDao.getUserByIdAndPwd(user);
	}
	
	/* 아이디 중복 체크 */
	@Override
	public int idCheck(String username) {
		return userDao.idCheck(username);
	}

	/* Spring Security에서 User 정보를 읽을 때 사용함. */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.readUserById(username);
		user.setAuthorities(getAuthorities(username));
		return user;
	}

	/* 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함 */
	@Override
	public Collection<GrantedAuthority> getAuthorities(String username) {
		List<String> string_authorities = userDao.readAuthorities(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : string_authorities) {
			authorities.add(new SimpleGrantedAuthority(authority));
        }
		return authorities;
	}
}