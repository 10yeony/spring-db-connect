package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.UsingLog;

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
	 * @return 수정된 row의 수
	 */
	public int updateMember(Member member);
	
	/**
	 * 파일 업로드를 포함한 회원 정보 수정
	 * @param member 회원정보
	 * @return 수정된 row의 수
	 */
	public int updateMemberWithFileUpload(Member member);
	
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
	 * 회원 정보를 페이징 처리하여 가져옴
	 * @return 모든 회원 정보값을 리스트로 담아 리턴
	 */
	public List<Member> getMemberList();

	/**
	 * 회원 정보를 모두 가져옴
	 * @return 회원 목록
	 */
	public List<Member> getAllMemberList();
	
	/**
	 * 검색어, 권한명, 승인 여부로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 검색어, 권한명, 승인 여부에 따른 회원 목록
	 */
	public List<Member> getMemberList(String role, 
										int limit, 
										int offset,
										String search_word, 
										String approval);

	/**
	 * 회원 수를 가져옴
	 * @return 회원 수
	 */
	public int getMemberCount();
	
	/**
	 * 검색어, 권한명, 승인 여부로 회원 수를 가져옴
	 * @param role 권한명
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 검색어, 권한명에 따른 회원 수
	 */
	public int getMemberCount(String role, String search_word, String approval);
	
	/**
	 * 사용 로그에 기록함
	 * @param usingLog 사용 로그에 기록할 UsingLog 객체
	 * @return DB에 추가된 row의 수
	 */
	public int insertIntoUsingLog(UsingLog usingLog);
	
	/**
	 * 사용 로그를 모두 가져옴
	 * @param member_id 사용자 아이디
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 사용 로그 목록
	 */
	public List<UsingLog> getAllUsingLog(String member_id,
										int limit, 
										int offset,
										String search_word);
	
	/**
	 * 아이피, 시간, 내용으로 사용 로그를 가져옴
	 * @param usingLog 아이피, 시간, 내용이 담긴 사용 로그
	 * @return 아이피, 시간, 내용으로 가져온 사용 로그
	 */
	public UsingLog getUsingLog(UsingLog usingLog);
	
	/**
	 * 사용 로그 총 개수를 가져옴
	 * @param member_id 사용자 아이디
	 * @param search_word 검색어
	 * @return 사용 로그 총 개수
	 */
	public int getAllCountOfUsingLog(String member_id, String search_word);

	/**
	 * 관리자 권한으로 가입 승인
	 * @param member_id 회원 id
	 * @return update된 row수
	 */
	public int updateMemberApprovalUsingId(String member_id);

	/**
	 * 로그인 할 때마다 마지막 로그인 시간을 업데이트
	 * @param member_id 업데이트 할 계정 id
	 */
	public void updateLoginTime(String member_id, String date);

	/**
	 * 3개월 이상 접속하지 않은 계정 만료
	 * @param member_id 만료할 계정 ID
	 */
	public void accountExpired(String member_id);

	/**
	 * 관리자 권한으로 계정 활성화
	 * @param member_id 활성화 할 member_id
	 */
	public int updateAccountActivation(String member_id);
}
