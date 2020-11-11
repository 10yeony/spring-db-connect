package kr.com.inspect.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.LoginDao;
import kr.com.inspect.dto.Member;
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
	public int registerMember(Member member) {
		int result = 0;
		result = postgreInsertMapper.registerMember(member);
		return result;
	}
	
	/* 로그인 */
	@Override
	public Member login(Member member) {
		Member result = null;
		result = postgreSelectMapper.login(member);
		return result;
	}
	
	/* 아이디 중복확인 */
	@Override
	public int IdCheck(String member_id) {
		int result = 0;
		result = postgreSelectMapper.IdCheck(member_id);
		return result;
	}
	
}
