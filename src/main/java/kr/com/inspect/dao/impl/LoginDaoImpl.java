package kr.com.inspect.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.User;
import kr.com.inspect.mapper.PostgreInsertMapper;
import kr.com.inspect.mapper.PostgreSelectMapper;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	private PostgreInsertMapper postgreInsertMapper;
	
	@Autowired
	private PostgreSelectMapper postgreSelectMapper;

	/* 회원가입 */
	@Override
	public int insertuser(User user) {
		int result = 0;
		result = postgreInsertMapper.insertUser(user);
		return result;
	}
	
	/* 로그인 */
	@Override
	public User login(User user) {
		User result = null;
		result = postgreSelectMapper.login(user);
		return result;
	}
	
	/* 아이디 중복확인 */
	@Override
	public User IdCheck(String id) {
		return null;
	}
	
}
