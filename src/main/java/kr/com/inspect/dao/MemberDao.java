package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;

public interface MemberDao {
	/* member 가입 */
	public int registerMember(Member member);
	
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
}
