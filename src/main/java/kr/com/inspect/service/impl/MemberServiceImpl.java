package kr.com.inspect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;


@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	/* 회원가입 */
	public int registerMember(Member member) {
		return memberDao.registerMember(member);
	}
	
	/* 로그인 */
	public Member loginMember(Member member){
		return memberDao.login(member);
	}
	
	/* 아이디 중복 체크 */
	public int IdCheck(String userid) {
		return memberDao.IdCheck(userid);
	}
}