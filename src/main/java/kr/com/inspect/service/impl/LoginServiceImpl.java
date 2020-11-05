package kr.com.inspect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.User;
import kr.com.inspect.service.LoginService;


@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDao logindao;
	
	/* 회원가입 */
	public int insertUser(User user) {
		return logindao.insertuser(user);
	}
	
	/* 로그인 */
	public User loginUser(User user){
		return logindao.login(user);
	}
}