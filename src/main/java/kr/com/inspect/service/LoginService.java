package kr.com.inspect.service;

import kr.com.inspect.dto.User;

public interface LoginService {
	/* 회원가입 */
	public  int insertUser(User user); 
	
	/* 로그인 */
	public User loginUser(User user); 
	
	/* 아이디 중복 체크 */
	public int IdCheck(String userid);
}
