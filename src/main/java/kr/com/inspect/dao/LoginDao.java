package kr.com.inspect.dao;

import kr.com.inspect.dto.User;

public interface LoginDao {
	/* 회원가입 */
	public int insertuser(User user);
	
	/* 로그인 */
	public User login(User user);

	/* 아이디 중복확인 */
	public int IdChk(User user) throws Exception;
}
