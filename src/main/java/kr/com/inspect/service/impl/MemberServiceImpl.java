package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/* 사용할 PasswordEncoder를 리턴해줌 */
	public PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}
	
	/* 회원가입 */
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
	
	/* 아이디로 회원정보를 가져옴 */
	@Override
	public Member readMemberById(String member_id){
		Member member = memberDao.readMemberById(member_id);
		member.setAuthorities(getAuthorities(member_id));
		return member;
	}
	
	/* 아이디 중복 체크 */
	@Override
	public int idCheck(String member_id) {
		return memberDao.idCheck(member_id);
	}

	/* 회원 정보 수정 */
	public int updateMember(Member member) {
		/* 비밀번호를 암호화함 */
		String rawPassword = member.getPassword(); //사용자가 입력한 raw한 비밀번호
		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword); //암호화된 비밀번호
		member.setPwd(encodedPassword); //암호화된 비밀번호로 세팅
		return memberDao.updateMember(member);
	}
	
	/* 비밀번호 변경 */
	public int updatePwd(String member_id, String pwd) {
		return memberDao.updatePwd(member_id, pwd);
	}
	
	/* 회원 탈퇴 */
	public int deleteMember(String member_id) {
		int result = 0;
		
		/* 모든 권한 삭제 */
		result += memberDao.deleteAuthorities(member_id);
		
		/* member 삭제 */
		result += memberDao.deleteMember(member_id);
		
		return result;
	}
	
	/* Spring Security에서 User 정보를 읽을 때 사용함. */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member vo = memberDao.readMemberById(username);
		vo.setAuthorities(getAuthorities(username));
		return vo;
	}

	/* 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함 */
	@Override
	public Collection<GrantedAuthority> getAuthorities(String member_id) {
		List<String> string_authorities = memberDao.readAuthorities(member_id);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : string_authorities) {
			authorities.add(new SimpleGrantedAuthority(authority));
        }
		return authorities;
	}

	@Override
	public List<Member> getMember() {
		return memberDao.getMember();
	}
}