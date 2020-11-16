package kr.com.inspect.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDao memberDao;
	
	/* 회원가입 */
	@Override
	public int registerMember(Member member) {
		return memberDao.registerMember(member);
	}
	
	/* 로그인 */
	@Override
	public Member loginMember(Member member){
		return memberDao.getMemberByIdAndPwd(member);
	}
	
	/* 아이디 중복 체크 */
	@Override
	public int idCheck(String member_id) {
		return memberDao.idCheck(member_id);
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
}