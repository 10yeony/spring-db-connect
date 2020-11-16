package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.User;

public interface UserDao {
	/* 회원가입 */
	public int registerUser(User user);
	
	/* 아이디로 회원정보를 읽음 */
	public User readUserById(String username);

	/* 아이디와 비밀번호로 회원정보를 가져옴(로그인) */
	public User getUserByIdAndPwd(User user);

	/* 아이디 중복확인 */
	public int idCheck(String username);
	
	/* id로 가지고 있는 권한들을 가져옴 */
	public List<String> readAuthorities(String username);
}
