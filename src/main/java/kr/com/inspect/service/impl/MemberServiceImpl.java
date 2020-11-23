package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	/**
	 * 
	 */
	@Autowired
	private MemberDao memberDao;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/**
	 * 사용할 PasswordEncoder를 리턴해줌
	 */
	@Override
	public PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}
	
	/**
	 * 회원가입
	 */
	@Override
	public int registerMember(Member member) {
		int result = 0;
		
		/* member 추가 */
		String rawPassword = member.getPassword(); //사용자가 입력한 raw한 비밀번호
		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword); //암호화된 비밀번호
		member.setPwd(encodedPassword); //암호화된 비밀번호로 세팅
		member.setAccountNonExpired(true); //계정 관련 기본값 true로 세팅
		member.setAccountNonLocked(true);
		member.setCredentialsNonExpired(true);
		member.setEnabled(true);
		result += memberDao.registerMember(member);
		
		/* 권한 추가 */
		result += memberDao.registerAuthority(member.getMember_id(), "ROLE_VIEW");
		
		return result;
	}
	
	/**
	 * 아이디로 회원정보를 가져옴
	 */
	@Override
	public Member readMemberById(String member_id){
		Member member = memberDao.readMemberById(member_id);
		member.setAuthorities(getAuthorities(member_id));
		return member;
	}
	
	/**
	 * 회원가입시 해당 요소가 DB에 존재하는지 중복 체크
	 */
	@Override
	public int registerCheck(String object, String value) {
		if(object.equals("id")) { //아이디 중복 체크
			return memberDao.idCheck(value);
		}else if(object.equals("email")) { //이메일 중복 체크
			return memberDao.emailCheck(value);
		}else if(object.contains("phone")) { //연락처 중복 체크
			return memberDao.phoneCheck(value);
		}else {
			return 0;
		}
	}

	/**
	 * 회원 정보 수정
	 */
	@Override
	public int updateMember(HttpSession session, Member member) {
		Member vo = (Member) session.getAttribute("member");
		member.setMember_id(vo.getMember_id()); //세션에서 아이디, 비밀번호를 가져옴
		member.setPwd(vo.getPwd());
		member.setAccountNonExpired(true); //계정 관련 기본값 true로 세팅
		member.setAccountNonLocked(true);
		member.setCredentialsNonExpired(true);
		member.setEnabled(true);
		//System.out.println(member);
		return memberDao.updateMember(member);
	}
	
	/**
	 * 관리자 권한으로 회원 권한 수정
	 */
	@Override
	public int updateAuthorities(String member_id, String[] authoritiesArr) {		
		int result = 0;
		memberDao.deleteAuthorities(member_id); //멤버 아이디로 모든 권한을 삭제함
		for(String authority : authoritiesArr) {
			result += memberDao.registerAuthority(member_id, authority);
		}
		return result;
	}

	/**
	 * 비밀번호 변경
	 */
	@Override
	public int updatePwd(String member_id, String pwd) {
		String encodedPassword = new BCryptPasswordEncoder().encode(pwd); //사용자가 입력한 비밀번호를 암호화
		return memberDao.updatePwd(member_id, encodedPassword);
	}
	
	/**
	 * 회원 탈퇴 
	 */
	@Override
	public void deleteMember(String member_id) {
		
		/* 모든 권한 삭제 */
		memberDao.deleteAuthorities(member_id);
		//System.out.println("권한 삭제 : "+result);
		
		/* member 삭제 */
		memberDao.deleteMember(member_id);
		//System.out.println("+회원 삭제 : "+result);
	}
	
	/**
	 * Spring Security에서 User 정보를 읽을 때 사용함.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member vo = memberDao.readMemberById(username);
		vo.setAuthorities(getAuthorities(username));
		return vo;
	}

	/**
	 * 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities(String member_id) {
		List<String> string_authorities = memberDao.readAuthorities(member_id);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : string_authorities) {
			authorities.add(new SimpleGrantedAuthority(authority));
        }
		return authorities;
	}

	/**
	 * 회원 정보를 모두 가져옴
	 * @return 회원 목록
	 */
	@Override
	public List<Member> getMemberList() {
		List<Member> list = memberDao.getMemberList();
		return list;
	}
	
	/**
	 * 권한명으로 회원 정보를 모두 가지고 옴
	 * @param role 권한명
	 * @return 해당 권한을 가진 회원 목록
	 */
	@Override
	public List<Member> getMemberListUsingRole(String role){
		List<Member> list = memberDao.getMemberListUsingRole(role);
		return list;
	}
}