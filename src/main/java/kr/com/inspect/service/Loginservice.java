package kr.com.inspect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.impl.LoginDaoImpl;
import kr.com.inspect.dto.User;


@Service
public class Loginservice {

	@Autowired
	LoginDaoImpl logindaoimpl;

	public int insertUser(User user) {
		return logindaoimpl.insertuser(user);
	}
	public User loginUser(User user){
		return logindaoimpl.login(user);
	}
}