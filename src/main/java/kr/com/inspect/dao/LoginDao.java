package kr.com.inspect.dao;

import java.util.Map;

import kr.com.inspect.dto.User;

public interface LoginDao {
	public int insertuser(User user);
	public User login(User user);
}
