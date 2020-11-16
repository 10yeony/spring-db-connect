package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;

public interface MemberDao {
	/* 회원가입 */
	public int registerMember(Member member);
	
	/* 아이디로 회원정보를 가져옴 */
	public Member getMemberById(Member member);

	/* 아이디와 비밀번호로 회원정보를 가져옴(로그인) */
	public Member getMemberByIdAndPwd(Member member);

	/* 아이디 중복확인 */
	public int IdCheck(String member_id);
	
	/* id로 가지고 있는 권한들을 가져옴 */
	public List<String> getAuthority(String memeber_id);
}
