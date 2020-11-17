package kr.com.inspect.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final String authorityNs = "AuthorityMapper.";

	/* member 가입 */
	@Override
	public int registerMember(Member member) {
		return sqlSession.insert(memberNs+"registerMember", member);
	}
	
	/* member 탈퇴 */
	public int deleteMember(String member_id) {
		return sqlSession.delete(memberNs+"deleteMember", member_id);
	}

	/* 권한 등록 */
	public int registerAuthority(String member_id, String authority) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", member_id);
		map.put("authority_name", authority);
		return sqlSession.insert(authorityNs+"registerAuthority", map);
	}
	
	/* 권한을 모두 삭제 */
	public int deleteAuthorities(String member_id) {
		int result = 0;
		return sqlSession.delete(authorityNs+"deleteAuthorities", member_id);
	}
	
	/* 아이디로 회원정보를 읽음 */
	@Override
	public Member readMemberById(String member_id) {
		Member result = null;
		result = sqlSession.selectOne(memberNs+"readMemberById", member_id);
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
		return sqlSession.selectList(authorityNs+"readAuthorities", member_id);
	}

	/* 회원 정보 모두 가지고옴  */
	public List<Member> getMember(){ return sqlSession.selectList(memberNs+"getMemberTable"); }
}
