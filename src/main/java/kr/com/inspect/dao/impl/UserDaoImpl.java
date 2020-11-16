package kr.com.inspect.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.UserDao;
import kr.com.inspect.dto.User;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private SqlSession sqlSession;
	private final String userNs = "UserMapper.";

	/* 회원가입 */
	@Override
	public int registerUser(User user) {
		int result = 0;
		result = sqlSession.insert(userNs+"registerUser", user);
		return result;
	}
	
	/* 아이디로 회원정보를 가져옴 */
	@Override
	public User readUserById(String username) {
		User result = null;
		result = sqlSession.selectOne(userNs+"readUserById", username);
		return result;
	}
	
	/* 아이디와 비밀번호로 회원정보를 가져옴(로그인) */
	@Override
	public User getUserByIdAndPwd(User user) {
		User result = null;
		result = sqlSession.selectOne(userNs+"getUserByIdAndPwd", user);
		return result;
	}
	
	/* 아이디 중복확인 */
	@Override
	public int idCheck(String username) {
		int result = 0;
		result = sqlSession.selectOne(userNs+"idCheck", username);
		return result;
	}

	/* id로 가지고 있는 권한들을 가져옴 */
	@Override
	public List<String> readAuthorities(String username) {
		return sqlSession.selectList(userNs+"readAuthorities", username);
	}
}
