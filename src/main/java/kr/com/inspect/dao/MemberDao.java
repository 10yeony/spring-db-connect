package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;

/**
 * 회원정보 DAO Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface MemberDao {
	/**
	 * member 가입
	 * @param member 회원정보
	 * @return 회원정보 리턴
	 */
	public int registerMember(Member member);
	
	/**
	 * 회원 정보 수정
	 * @param member 회원정보
	 * @return 회원정보 리턴
	 */
	public int updateMember(Member member);
	
	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀번호
	 * @return 회원의 아이디와 비밀번호 값 리턴ㄴ
	 */
	public int updatePwd(String member_id, String pwd);
	
	/**
	 * member 탈퇴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 리턴
	 */
	public int deleteMember(String member_id);
	
	/**
	 * 권한 등록
	 * @param member_id 회원 아이디
	 * @param authority 권한부여
	 * @return 회원 아이디와 권한부여 값을 리턴
	 */
	public int registerAuthority(String member_id, String authority);
	
	/**
	 * 권한을 모두 삭제
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값을 리턴
	 */
	public int deleteAuthorities(String member_id);
	
	/**
	 * 아이디로 회원정보를 읽음
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값을 리턴
	 */
	public Member readMemberById(String member_id);

	/**
	 * 아이디 중복확인
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값을 리턴
	 */
	public int idCheck(String member_id);
	
	/**
	 * 이메일 중복확인
	 * @param email 이메일 정보
	 * @return 이메일 정보 값을 리턴
	 */
	public int emailCheck(String email);
	
	/**
	 * 연락처 중복확인
	 * @param phone 연락처 정보
	 * @return 연락처 정보 값을 리턴
	 */
	public int phoneCheck(String phone);
	
	/**
	 * id로 가지고 있는 권한들을 가져옴
	 * @param memeber_id 회원 아이디
	 * @return 회원 아이디에 저장되어 있는 권한 값을 리스트로 담아 리턴
	 */
	public List<String> readAuthorities(String memeber_id);

	/**
	 * 회원 정보 모두 가지고 옴
	 * @return 모든 회원 정보값을 리스트로 담아 리턴
	 */
	public List<Member> getMemberList();
	
	/**
	 * 권한명으로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @return 해당 권한을 가진 회원 목록
	 */
	public List<Member> getMemberListUsingRole(String role);

	/**
	 * 회원 수를 가져옴
	 * @return 회원 수
	 */
	public int getMemberCount();
}
