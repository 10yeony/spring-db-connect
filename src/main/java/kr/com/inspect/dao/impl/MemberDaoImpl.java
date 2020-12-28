package kr.com.inspect.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.UsingLog;

/**
 * 회원정보 DAO
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Repository
public class MemberDaoImpl implements MemberDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String memberNs = "MemberMapper.";
	private final String authorityNs = "AuthorityMapper.";
	private final String usingLogNs = "UsingLogMapper.";

	/**
	 * member 회원가입
	 * @param member 회원 정보
	 * @return 회원 정보값을 세션값 함께 리턴
	 */
	@Override
	public int registerMember(Member member) {
		return sqlSession.insert(memberNs+"registerMember", member);
	}
	
	/**
	 * 회원 정보 수정
	 * @param member 회원정보
	 * @return 회원정보값을 세션값과 함께 리턴
	 */
	public int updateMember(Member member) {
		return sqlSession.update(memberNs+"updateMember", member);
	}
	
	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀번호
	 * @return 회원 비밀번호 값을 세션값과 함께 리턴
	 */
	public int updatePwd(String member_id, String pwd) {
		Map<String, String> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("pwd", pwd);
		return sqlSession.update(memberNs+"updatePwd", map);
	}
	
	/**
	 * member 탈퇴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값과 세션값을 리턴
	 */
	public int deleteMember(String member_id) {
		return sqlSession.delete(memberNs+"deleteMember", member_id);
	}

	/**
	 * 권한 등록 
	 * @param member_id 회원 아이디
	 * @param authority 권한
	 * @return 회원 아이디와 권한 값을 map으로 담아 세션값과 함께 리턴 
	 */
	public int registerAuthority(String member_id, String authority) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", member_id);
		map.put("authority_name", authority);
		return sqlSession.insert(authorityNs+"registerAuthority", map);
	}
	
	/**
	 * 권한을 모두 삭제
	 * @param member_id 회원 아이디
	 * @return 회원아이디에 해당하는 권한을 0으로 설정후 세션값과 함께 리턴
	 */
	public int deleteAuthorities(String member_id) {
		int result = 0;
		return sqlSession.delete(authorityNs+"deleteAuthorities", member_id);
	}
	
	/**
	 * 아이디로 회원정보를 읽음
	 * @param member_id 회원 아이디
	 * @return 회원 아이디로 읽어온 회원 정보와 세션값을 변수 member에 담아 값을 리턴
	 */
	@Override
	public Member readMemberById(String member_id) {
		Member member = null;
		member = sqlSession.selectOne(memberNs+"readMemberById", member_id);
		return member;
	}
	
	/**
	 * 아이디 중복확인
	 * @param member_id 회원아이디
	 * @return 회원 아이디로 중복 확인 후 변수 result에 담아 값을 리턴
	 */
	@Override
	public int idCheck(String member_id) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"idCheck", member_id);
		return result;
	}
	
	/**
	 * 이메일 중복확인
	 * @param email 이메일 정보
	 * @return 이메일 정보 중복 확인 후 변수 result에 담아 값을 리턴
	 */
	public int emailCheck(String email) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"emailCheck", email);
		return result;
	}
	
	/**
	 * 연락처 중복확인
	 * @param phone 연락처 정보
	 * @return 연락처 정보 중복확인 후 변수 result 값에 담아 리턴
	 */
	public int phoneCheck(String phone) {
		int result = 0;
		result = sqlSession.selectOne(memberNs+"phoneCheck", phone);
		return result;
	}

	/**
	 * id로 가지고 있는 권한들을 가져옴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디로 권한정보를 불러온후 세션값과 함께 리스트로 담아 리턴
	 */
	@Override
	public List<String> readAuthorities(String member_id) {
		return sqlSession.selectList(authorityNs+"readAuthorities", member_id);
	}
	
	/**
	 * 회원 정보 모두 가지고옴 
	 * @return 회원 목록
	 */
	@Override
	public List<Member> getMemberList(){ 
		return sqlSession.selectList(memberNs+"getMemberList"); 	
	}
	
	/**
	 * 검색어, 권한명, 승인 여부로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 검색어, 권한명, 승인 여부에 따른 회원 목록
	 */
	@Override
	public List<Member> getMemberList(String role, 
										int limit, 
										int offset,
										String search_word, 
										String approval){
		List<Member> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", role);
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("search_word", search_word);
		if(role.equals("ALL")) { //권한과 관계 없이 가져오기
			if(approval.equals("")) { //승인 여부에 관계 없이 가져오기
				list = sqlSession.selectList(memberNs+"getMemberList", map);
			}else { //승인 여부에 따라 가져오기
				map.put("approval", Boolean.parseBoolean(approval));
			}
		}else { //권한에 따라 가져오기
			if(approval.equals("")) { //승인 여부에 관계 없이 가져오기
				list = sqlSession.selectList(memberNs+"getMemberListUsingRole", map);
			}else { //승인 여부에 따라 가져오기
				map.put("approval", Boolean.parseBoolean(approval));
			}
		}
		return list;
	}

	/**
	 * 회원 수를 가져옴
	 * @return 회원 수
	 */
	@Override
	public int getMemberCount(){
		return sqlSession.selectOne(memberNs+"getMemberCount");
	}
	
	/**
	 * 검색어, 권한명, 승인 여부로 회원 수를 가져옴
	 * @param role 권한명
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 검색어, 권한명에 따른 회원 수
	 */
	@Override
	public int getMemberCount(String role, String search_word, String approval) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_word", search_word);
		if(role.equals("ALL")) { //권한과 관계 없이 가져오기
			if(approval.equals("")) { //승인 여부에 관계 없이 가져오기
				result = sqlSession.selectOne(memberNs+"getMemberSearchCount", map);
			}else { //승인 여부에 따라 가져오기
				map.put("approval", Boolean.parseBoolean(approval));
			}
		}else { //권한에 따라 가져오기
			map.put("role", role);
			if(approval.equals("")) { //승인 여부에 관계 없이 가져오기
				result = sqlSession.selectOne(memberNs+"getMemberCountUsingRole", map);
			}else { //승인 여부에 따라 가져오기
				map.put("approval", Boolean.parseBoolean(approval));
			}
		}
		return result;
	}

	/**
	 * 사용 로그에 기록함
	 * @param usingLog 사용 로그에 기록할 UsingLog 객체
	 * @return DB에 추가된 row의 수
	 */
	@Override
	public int insertIntoUsingLog(UsingLog usingLog) {
		return sqlSession.insert(usingLogNs+"insertIntoUsingLog", usingLog);
	}
	
	/**
	 * 사용 로그를 모두 가져옴
	 * @param member_id 사용자 아이디
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 사용 로그 목록
	 */
	@Override
	public List<UsingLog> getAllUsingLog(String member_id,
										int limit, 
										int offset,
										String search_word){
		List<UsingLog> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("search_word", search_word);
		if(member_id == "") {
			list = sqlSession.selectList(usingLogNs+"getAllUsingLog", map);
		}else {
			map.put("member_id", member_id);
			list = sqlSession.selectList(usingLogNs+"getAllUsingLogByMemberId", map);
		}
		return list;
	}
	
	/**
	 * 아이피, 시간, 내용으로 사용 로그를 가져옴
	 * @param usingLog 아이피, 시간, 내용이 담긴 사용 로그
	 * @return 아이피, 시간, 내용으로 가져온 사용 로그
	 */
	@Override
	public UsingLog getUsingLog(UsingLog usingLog) {
		return sqlSession.selectOne(usingLogNs+"getUsingLog", usingLog);
	}
	
	/**
	 * 사용 로그 총 개수를 가져옴
	 * @param member_id 사용자 아이디
	 * @param search_word 검색어
	 * @return 사용 로그 총 개수
	 */
	@Override
	public int getAllCountOfUsingLog(String member_id, String search_word) {
		int count = 0;
		if(member_id == "") {
			count = sqlSession.selectOne(usingLogNs+"getAllCountOfUsingLog", search_word);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("member_id", member_id);
			map.put("search_word", search_word);
			count = sqlSession.selectOne(usingLogNs+"getAllCountOfUsingLogByMemberId", map);
		}
		return count;
	}
	
	/**
	 * 관리자 권한으로 가입 승인
	 * @param member_id 회원 id
	 * @return update된 row수
	 */
	@Override
	public int updateMemberApprovalUsingId(String member_id){
		return sqlSession.update(memberNs+"updateMemberApprovalUsingId", member_id);
	}

	/**
	 * 로그인 할 때마다 마지막 로그인 시간을 업데이트
	 * @param member_id 업데이트 할 계정 id
	 */
	@Override
	public void updateLoginTime(String member_id, String date){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_id", member_id);
		map.put("date", date);
		sqlSession.update(memberNs+"updateLoginTime", map);
	}
}
