package kr.com.inspect.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
	private SqlSession sqlSession;
	private final String memberNs = "MemberMapper.";

	/* 회원가입 */
	@Override
	public int registerMember(Member member) {
		int result = 0;
		result = sqlSession.insert(memberNs+"registerMember", member);
		return result;
	}
	
	/* 아이디로 회원정보를 가져옴 */
	@Override
	public Member readMemberById(String member_id) {
		Member result = null;
		result = sqlSession.selectOne(memberNs+"getMemberById", member_id);
		return result;
	}
	
	/* 아이디와 비밀번호로 회원정보를 가져옴(로그인) */
	@Override
	public Member getMemberByIdAndPwd(Member member) {
		Member result = null;
		result = sqlSession.selectOne(memberNs+"getMemberByIdAndPwd", member);
		return result;
	}
	
	/* 아이디 중복확인 */
	@Override
	public int idCheck(String member_id) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"idCheck", member_id);
		return result;
	}

	/* id로 가지고 있는 권한들을 가져옴 */
	@Override
	public List<String> readAuthorities(String member_id) {
		return sqlSession.selectList(memberNs+"getAuthorities", member_id);
	}

	/* 회원 정보 모두 가지고옴  */
	public List<Member> getMember(){ return sqlSession.selectList(memberNs+"getMemberTable"); }
}
