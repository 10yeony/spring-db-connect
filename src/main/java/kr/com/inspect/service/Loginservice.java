package kr.com.inspect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.User;


@Service
public class Loginservice {

	@Autowired
	LoginDao logindao;

	public int insertUser(User user) {
		return logindao.insertuser(user);
	}
	public User loginUser(User user){
		return logindao.login(user);
	}
}