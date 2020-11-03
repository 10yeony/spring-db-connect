package kr.com.inspect.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.AccountDao;
import kr.com.inspect.dto.Account;

@Repository
public class AccountRepository {

	@Autowired
	AccountDao accountdao;

	public Account findById(String username) {
		return accountdao.readAccount(username);
	}
}
