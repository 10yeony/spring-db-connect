package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;

public interface MemberDao {
	/* member 가입 */
	public int registerMember(Member member);
	
	/* 회원 정보 수정 */
	public int updateMember(Member member);
	
	/* 비밀번호 변경 */
	public int updatePwd(String member_id, String pwd);
	
	/* member 탈퇴 */
	public int deleteMember(String member_id);
	
	/* 권한 등록 */
	public int registerAuthority(String member_id, String authority);
	
	/* 권한을 모두 삭제 */
	public int deleteAuthorities(String member_id);
	
	/* 아이디로 회원정보를 읽음 */
	public Member readMemberById(String member_id);

	/* 아이디 중복확인 */
	public int idCheck(String member_id);
	
	/* id로 가지고 있는 권한들을 가져옴 */
	public List<String> readAuthorities(String memeber_id);

	/* 회원 정보 모두 가지고옴  */
	public List<Member> getMember();
}
