package kr.com.inspect.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.paging.CommonDto;
import kr.com.inspect.paging.PagingResponse;
import kr.com.inspect.sender.SendMail;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.util.ClientInfo;
import kr.com.inspect.util.RandomKey;
import kr.com.inspect.util.UsingLogUtil;

/**
 * 회원 Service
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
@Service("memberService")
@PropertySource(value = "classpath:properties/directory.properties")
public class MemberServiceImpl implements MemberService {
	
	/**
	 * 로그 출력을 위한 logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	/**
	 * 회원 dao 필드 선언
	 */
	@Autowired
	private MemberDao memberDao;
	
	/**
	 * 사용자 정보(아이피, 아이디, 암호화된 비밀번호)와 관련된 객체
	 */
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * 사용자의 사용 로그 기록을 위한 UsingLogUtil 객체
	 */
	@Autowired
	private UsingLogUtil usingLogUtil;
	
	/**
	 * 페이징 처리 응답 객체
	 */
	@Autowired
	private PagingResponse pagingResponse;
	
	/**
	 * 패스워드 인코더 필드 선언
	 */
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 메일 전송 객체
	 */
	@Autowired
	private SendMail sendMail;
	
	/**
	 * 사용자 개별로 특화된 경로
	 */
	@Value("${user.root.directory}")
	private String userPath;
	
	/**
	 * 프로필 이미지 디렉토리
	 */
	private String profileImgDir = "profileImg";
	
	/**
	 * 
	 * @return 사용할 PasswordEncoder를 리턴해줌
	 */
	@Override
	public PasswordEncoder passwordEncoder() {
		passwordEncoder = new BCryptPasswordEncoder();
		return this.passwordEncoder;
	}
	
	/**
	 * 회원가입
	 * @param member 회원 정보
	 * @return 회원 정보 가입 값 리턴
	 */
	@Override
	public int registerMember(MultipartFile[] uploadImgFile, Member member) {
		int result = 0;
		
		/* member 추가 */
		String rawPassword = member.getPassword(); //사용자가 입력한 raw한 비밀번호
		String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword); //암호화된 비밀번호
		member.setApproval("false"); // 가입 승인이 아직 안됨
		member.setPwd(encodedPassword); //암호화된 비밀번호로 세팅
		member.setAccountNonExpired(true); //계정 관련 기본값 true로 세팅
		member.setAccountNonLocked(true);
		member.setCredentialsNonExpired(true);
		member.setEnabled(true);
		
		String path = userPath + member.getMember_id() + File.separator + profileImgDir;
		logger.info(clientInfo.getTime() + " 회원가입 path : " + path);
		
		if(!uploadImgFile[0].getOriginalFilename().equals("")) {
			File fileDir = new File(path + File.separator); 
			logger.info(clientInfo.getTime() + " 회원가입 fileDir : " + fileDir.toString());
			if(!fileDir.exists()){
				logger.info(clientInfo.getTime() + " 회원가입 fileDir 존재하지 않음 : " + fileDir);
				fileDir.mkdir();
			}
			for (MultipartFile uploadImg : uploadImgFile) {
				String filename = uploadImg.getOriginalFilename();
				logger.info(clientInfo.getTime() + " 회원가입 filename : " + filename);
				member.setProfile_img(filename);
				File file= new File(path + File.separator + filename + File.separator);
				logger.info(clientInfo.getTime() + " 회원가입 file : " + file.toString());
				try {
					uploadImg.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					//e.printStackTrace();
				}
			}
		}
		
		result += memberDao.registerMember(member);
		sendMail.sendApproval(member);
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setMember_id(member.getMember_id());
			usingLog.setContent("회원가입");
			usingLogUtil.setUsingLog(usingLog);
		}
		
		/* 권한 추가 */
		result += memberDao.registerAuthority(member.getMember_id(), "ROLE_VIEW");
		
		return result;
	}
	
	/**
	 * 아이디로 회원정보를 가져옴
	 * @param member_id 회원 아이디
	 * @return 회원 아이디 값 리턴
	 */
	@Override
	public Member readMemberById(String member_id){
		Member member = memberDao.readMemberById(member_id);
		member.setAuthorities(getAuthorities(member_id));
		return member;
	}
	
	/**
	 * 회원가입시 해당 요소가 DB에 존재하는지 중복 체크
	 * @param object 회원 정보 객체
	 * @param value 값 여부 확인
	 * @return 중복체크 여부 후 리턴
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
	 * @param session 해당유저의 세션
	 * @param member 회원정보
	 * @return 수정된 회원정보 값 리턴
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
		int result = memberDao.updateMember(member);
		
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent("회원 정보 수정");
			usingLogUtil.setUsingLog(usingLog);
		}
		
		return result;
	}
	
	/**
	 * 관리자 권한으로 회원 권한 수정
	 * @param member_id 회원 아이디
	 * @param authoritiesArr 권한 배열
	 * @return 권한 수정 값 리턴
	 */
	@Override
	public int updateAuthorities(String member_id, String[] authoritiesArr) {
		int result = 0;
		memberDao.deleteAuthorities(member_id); //멤버 아이디로 모든 권한을 삭제함
		for(String authority : authoritiesArr) {
			result += memberDao.registerAuthority(member_id, authority);
		}
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent(member_id + " : 회원 권한 수정");
			usingLogUtil.setUsingLog(usingLog);
		}
		return result;
	}

	/**
	 * 비밀번호 변경
	 * @param member_id 회원 아이디
	 * @param pwd 회원 비밀번호
	 * @return 회원 아이디와 수정된 비밀번호 값을 리턴
	 */
	@Override
	public int updatePwd(String member_id, String pwd) {
		String encodedPassword = new BCryptPasswordEncoder().encode(pwd); //사용자가 입력한 비밀번호를 암호화
		int result = memberDao.updatePwd(member_id, encodedPassword);
		
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent("비밀번호 변경");
			usingLogUtil.setUsingLog(usingLog);
		}
		return result;
	}
	
	/**
	 * 비밀번호를 잊어버린 회원에게 아이디와 이메일을 받고 임시 비밀번호를 메일로 발송 
	 * @param member_id 회원 아이디
	 * @param email 회원 이메일
	 */
	@Override
	public String sendPwdToEmail(String member_id, String email) {
		Member member = memberDao.readMemberById(member_id);
		
		/* 인증번호 생성 */
		if(member == null) { //존재하지 않는 아이디
			return "idNotExist";
		}else if(!member.getEmail().equals(email)) { //이메일 불일치
			return "emailNotSame";
		}else {
			UsingLog usingLog = new UsingLog();
			usingLog.setMember_id(member_id);
			usingLog.setContent("임시 비밀번호 이메일 발송");
			usingLogUtil.setUsingLog(usingLog);
			
			/* 랜덤 비밀번호를 암호화하여 회원정보 수정 */
			String pwd = new RandomKey().getRamdomString(10); //랜덤 비밀번호 생성
			String encodedPassword = new BCryptPasswordEncoder().encode(pwd); //비밀번호 암호화
			member.setPwd(encodedPassword); //암호화된 랜덤 비밀번호로 세팅
			memberDao.updateMember(member); //회원정보 수정
						
			/* 이메일로 임시 비밀번호 발송 */
			try {
				sendMail.sendPwd(email, pwd);
				return "success";
			} catch (Exception e) {
				//e.printStackTrace();
				return "sendFailed";
			}
		}
	}
	
	/**
	 * 회원 탈퇴 
	 * @param member_id 회원 아이디
	 * @return 삭제 값 리턴
	 */
	@Override
	public void deleteMember(String member_id) {
		String username = clientInfo.getMemberId();
		
		/* 모든 권한 삭제 */
		int authDelResult = memberDao.deleteAuthorities(member_id);
		//System.out.println("삭제된 권한 개수 : " + authDelResult);
		
		/* member 삭제 */
		int memDelResult = memberDao.deleteMember(member_id);
		if(memDelResult == 1) {
			UsingLog usingLog = new UsingLog();
			usingLog.setMember_id(username);
			if(member_id.equals(username)) { //스스로 탈퇴된 경우
				usingLog.setMember_id("admin");
				usingLog.setContent(member_id + " : 회원 탈퇴(by " + member_id + ")");
			}else { //관리자에 의해 탈퇴된 경우
				usingLog.setContent(member_id + " : 회원 탈퇴(by 관리자)");
			}
			usingLogUtil.setUsingLog(usingLog);
		}
	}

	/**
	 * 스케쥴러로 인한 멤버 삭제
	 * @param member_id 삭제할 회원 아이디
	 */
	@Override
	public void deleteMemberByScheduler(String member_id){
		/* 모든 권한 삭제 */
		int authDelResult = memberDao.deleteAuthorities(member_id);
		//System.out.println("삭제된 권한 개수 : " + authDelResult);

		/* member 삭제 */
		int memDelResult = memberDao.deleteMember(member_id);

		if(memDelResult == 1){
			UsingLog usingLog = new UsingLog();
			usingLog.setMember_id("admin");
			usingLog.setContent(member_id + " : 회원 탈퇴(장기 미로그인)");
			usingLog.setIp_addr("45.32.55.180");
			usingLogUtil.setUsingLog(usingLog);
		}
	}
	
	/**
	 * Spring Security에서 User 정보를 읽을 때 사용함.
	 * @param username 유저 이름
	 * @exception UsernameNotFoundException 유저이름 예외처리
	 * @return vo 객체 값 리턴
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member vo = memberDao.readMemberById(username);
		vo.setAuthorities(getAuthorities(username));
		return vo;
	}

	/**
	 * 읽어온 회원정보에 대하여 권한을 부여한 뒤 리턴함
	 * @param member_id 회원 아이디
	 * @return 권한 부여후 리턴
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
	 * 회원 정보를 페이징 처리하여 가져옴
	 * @return 회원 목록
	 */
	@Override
	public List<Member> getMemberList() {
		List<Member> list = memberDao.getMemberList();
		return list;
	}

	/**
	 * 회원 정보를 모두 가져옴
	 * @return 회원 목록
	 */
	@Override
	public List<Member> getAllMemberList() {
		List<Member> list = memberDao.getAllMemberList();
		return list;
	}

	/**
	 * 사용자 목록 테이블을 페이징 처리하여 가져옴
	 * @param role 사용자 권한
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 사용자 목록 테이블
	 */
	@Override
	public ResponseData getMemberList(String role,
										String function_name, 
										int current_page_no,
										int count_per_page,
										int count_per_list,
										String search_word,
										String approval){
    	
		CommonDto commonDto = new CommonDto();
		int totalCount = memberDao.getMemberCount(role, search_word, approval);
		if (totalCount > 0) {
			commonDto = commonDto.setCommonDto(function_name, current_page_no, count_per_page, count_per_list, totalCount);
		}
		int limit = commonDto.getLimit();
		int offset = commonDto.getOffset();
		List<Member> list = memberDao.getMemberList(role, limit, offset, search_word, approval);
		String pagination = commonDto.getPagination();
		
		ResponseData responseData = pagingResponse.getResponseData(list, totalCount, pagination);
		return responseData;
	}
	
	/**
	 * 사용 로그 테이블을 페이징 처리하여 가져옴
	 * @param member_id 사용자 아이디
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 사용 로그 테이블
	 */
	public ResponseData getUsingLog(String member_id,
									String function_name, 
									int current_page_no,
									int count_per_page,
									int count_per_list,
									String search_word){
    	
		CommonDto commonDto = new CommonDto();
		int totalCount = memberDao.getAllCountOfUsingLog(member_id, search_word); 
		if (totalCount > 0) {
			commonDto = commonDto.setCommonDto(function_name, current_page_no, count_per_page, count_per_list, totalCount);
		}
		int limit = commonDto.getLimit();
		int offset = commonDto.getOffset();
		List<UsingLog> list = memberDao.getAllUsingLog(member_id, limit, offset, search_word);
		String pagination = commonDto.getPagination();
		
		ResponseData responseData = pagingResponse.getResponseData(list, totalCount, pagination);
		return responseData;
	}

	/**
	 * 관리자 권한으로 가입 승인
	 * @param member_id 회원 id
	 */
	@Override
	public void updateMemberApprovalUsingId(String member_id){
		int result = memberDao.updateMemberApprovalUsingId(member_id);
		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent(member_id + " : 가입 승인");
			usingLogUtil.setUsingLog(usingLog);
		}
	}
	
	/**
	 * 사용 로그에 로그아웃을 기록함
	 */
	@Override
	public void recordLogout() {
		UsingLog usingLog = new UsingLog();
		usingLog.setContent("로그아웃");
		usingLogUtil.setUsingLog(usingLog);
	}

	/**
	 * 로그인 할 때마다 마지막 로그인 시간을 업데이트
	 * @param member_id 업데이트 할 계정 id
	 */
	@Override
	public void updateLoginTime(String member_id){
		memberDao.updateLoginTime(member_id, clientInfo.getTime());
	}

	/**
	 * 3개월 이상 접속하지 않은 계정 만료
	 * @param member_id 만료할 계정 ID
	 */
	@Override
	public void accountExpired(String member_id){
		memberDao.accountExpired(member_id);
	}

	/**
	 * 관리자 권한으로 계정 활성화
	 * @param member_id 활성화 할 member_id
	 */
	@Override
	public void updateAccountActivation(String member_id){
		int result = memberDao.updateAccountActivation(member_id);

		if(result > 0) {
			UsingLog usingLog = new UsingLog();
			usingLog.setContent(member_id + " : 계정 활성화");
			usingLogUtil.setUsingLog(usingLog);
		}
	}
}