package kr.com.inspect.dao;

import kr.com.inspect.dto.Account;

public interface AccountDao {
	Account readAccount(String id);
}