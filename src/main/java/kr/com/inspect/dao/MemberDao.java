package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface MemberDao {
	/**
	 * member 가입
	 * @param member
	 * @return
	 */
	public int registerMember(Member member);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return
	 */
	public int updateMember(Member member);
	
	/**
	 * 비밀번호 변경
	 * @param member_id
	 * @param pwd
	 * @return
	 */
	public int updatePwd(String member_id, String pwd);
	
	/**
	 * member 탈퇴
	 * @param member_id
	 * @return
	 */
	public int deleteMember(String member_id);
	
	/**
	 * 권한 등록
	 * @param member_id
	 * @param authority
	 * @return
	 */
	public int registerAuthority(String member_id, String authority);
	
	/**
	 * 권한을 모두 삭제
	 * @param member_id
	 * @return
	 */
	public int deleteAuthorities(String member_id);
	
	/**
	 * 아이디로 회원정보를 읽음
	 * @param member_id
	 * @return
	 */
	public Member readMemberById(String member_id);

	/**
	 * 아이디 중복확인
	 * @param member_id
	 * @return
	 */
	public int idCheck(String member_id);
	
	/**
	 * 이메일 중복확인
	 * @param email
	 * @return
	 */
	public int emailCheck(String email);
	
	/**
	 * 연락처 중복확인
	 * @param phone
	 * @return
	 */
	public int phoneCheck(String phone);
	
	/**
	 * id로 가지고 있는 권한들을 가져옴
	 * @param memeber_id
	 * @return
	 */
	public List<String> readAuthorities(String memeber_id);

	/**
	 * 회원 정보 모두 가지고 옴
	 * @return
	 */
	public List<Member> getMemberList();
	
	/**
	 * 권한명으로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @return 해당 권한을 가진 회원 목록
	 */
	public List<Member> getMemberListUsingRole(String role);
}
