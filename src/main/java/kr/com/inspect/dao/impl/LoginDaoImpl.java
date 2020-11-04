package kr.com.inspect.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.User;
import kr.com.inspect.mapper.PostgreMapper;

@Repository
public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	private PostgreMapper postgreMapper;

	@Override
	public int insertuser(User user) {
		int result = 0;
		result = postgreMapper.insertuser(user);
		return result;
	}

	@Override
	public User login(User user) {
		User result=null;
		result = postgreMapper.login(user);
		return result;
	}
}
