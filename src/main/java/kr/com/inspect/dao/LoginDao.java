package kr.com.inspect.dao;

import kr.com.inspect.dto.User;

public interface LoginDao {
	public int insertuser(User user);
	public User login(User user);
}
