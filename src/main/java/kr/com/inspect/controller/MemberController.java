package kr.com.inspect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.util.ClientInfo;

/**
 * 로그인 기능 관련 컨트롤러
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Controller
public class MemberController {
	/**
	 * 멤버서비스의 필드 선언
	 */
	@Autowired
	private MemberService memberService;
	
	/**
	 * 사용자 정보와 관련된 객체
	 */
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * 커스텀 로그인 페이지로 이동(반드시 GET 방식이어야 함)
	 * @return 커스텀 로그인 페이지로 리턴
	 */
	@GetMapping("/login")
	public String Login() {
		return "login";
	}

	/**
	 * 회원가입
	 * @param member 회원정보
	 * @return 회원가입 후 성공/실패 메세지를 반환함
	 */
	@ResponseBody
	@RequestMapping(value = "/register", produces = "application/text; charset=utf8")
	public String registerMember(MultipartFile[] uploadImgFile, Member member) {
		String msg = "회원가입에 실패하였습니다.";
		int result = memberService.registerMember(uploadImgFile, member);
		if (result == 2) { //DB에 있는 회원테이블 정보와 비교시 회원정보가 없을 경우
			msg = "회원가입 완료! 로그인해주세요.";
		}
		return msg; //실패 메세지
	}

	/**
	 *  회원가입시 해당 요소가 DB에 존재하는지 중복 체크 
	 * @param request 사용자로부터 들어온 요청
	 * @param object 해당 요소(아이디/이메일/연락처)
	 * @return 회원가입시 해당 요소의 DB 존재 여부(존재시 1)를 반환
	 */
	@ResponseBody
	@PostMapping("register/check/{object}")
	public String registerCheck(HttpServletRequest request, 
							@PathVariable String object) {
		int result = 0;
		String value = null;
		if(object.equals("id")) { //아이디 중복 체크
			value = request.getParameter("member_id");
		}else if(object.equals("email")) { //이메일 중복 체크
			value = request.getParameter("email");
		}else if(object.equals("phone")){ //연락처 중복 체크
			value = request.getParameter("phone");
		}
		result = memberService.registerCheck(object, value);
		return Integer.toString(result);
	}
	
	/**
	 * 회원정보를 수정하거나 삭제할 때 비밀번호를 입력받고 자격을 확인함
	 * @param session 해당유저의 세션
	 * @param pwd 비밀번호
	 * @return ajax로 회원정보 수정 및 탈퇴 자격(true/false)을 입증함
	 */
	@ResponseBody
	@PostMapping("/ableToEdit")
	public String ableToEdit(HttpSession session, String pwd) {
		String password = clientInfo.getPwd();
		PasswordEncoder pwdEncoder = memberService.passwordEncoder(); 
		boolean pwdMatch = pwdEncoder.matches(pwd, password); // 비밀번호 비교
		
		if(pwdMatch) {
			return "true";
		}else {
			return "false";	
		}
	}
	
	/**
	 * 회원정보를 수정함
	 * @param session 해당유저의 세션
	 * @param member 회원정보
	 * @return ajax로 회원정보 수정 여부(true/false)를 반환 
	 */
	@ResponseBody
	@PostMapping("/updateMember")
	public String UpdateMember(HttpSession session, 
						MultipartFile[] uploadImgFile, 
						Member member, boolean changeToDefaultImg) {
		int result = memberService.updateMember(uploadImgFile, member, changeToDefaultImg);
		if(result==1) {
			Member vo = memberService.readMemberById(member.getMember_id());
			session.setAttribute("member", vo); //회원정보를 수정했으므로 세션 재설정
			return "true";
		}else {
			return "false";
		}
	}
	
	/**
	 * 비밀번호를 수정함
	 * @param session 해당유저의 세션
	 * @param pwd 해당유저의 비밀번호
	 * @return ajax로 비밀번호 수정 여부(true/false)를 반환
	 */
	@ResponseBody
	@PostMapping("/updatePwd")
	public String UpdatePwd(HttpSession session, String pwd) {
		Member member = (Member) session.getAttribute("member");
		int result = memberService.updatePwd(member.getMember_id(), pwd);
		if(result == 1) {
			session.invalidate(); //비밀번호를 수정하였으므로 다시 로그인하게 함
			return "true";
		}else {
			return "false";
		}
	}
	
	/**
	 * 비밀번호를 잊어버린 회원에게 아이디와 이메일을 받고 임시 비밀번호를 메일로 발송 
	 * @param member_id 사용자 아이디
	 * @param email 사용자 이메일 
	 * @return ajax로 결과(success/idNotExist/emailNotSame/sendFailed)를 반환
	 */
	@ResponseBody
	@PostMapping("/sendPwdToEmail")
	public String sendPwdToEmail(String member_id, String email) {
		String result = memberService.sendPwdToEmail(member_id, email);
		return result;
	}
	
	/**
	 * 관리자 권한으로 회원 권한 수정
	 * @param member_id 회원 아이디
	 * @param authorities 권한부여
	 * @return ajax로 회원권한 수정 여부(true/false)를 반환
	 */
	@ResponseBody
	@PostMapping("/updateAuthoritiesByAdmin")
	public String updateAuthoritiesByAdmin(String member_id, String authorities) {
		String[] authoritiesArr = authorities.split(",");
		int result = memberService.updateAuthorities(member_id, authoritiesArr);
		if(result == authoritiesArr.length) {
			return "true";
		}else {
			return "false";
		}
		
	}
	
	/**
	 * 회원을 삭제함
	 * @param session 해당유저의 세션
	 * @return ajax로 회원 탈퇴 여부(true/false)를 반환
	 */
	@ResponseBody
	@GetMapping("/deleteMember")
	public String deleteMember(HttpSession session) {
		Member member = (Member) session.getAttribute("member"); //해당 유저 세션 불러오기
		memberService.deleteMember(member.getMember_id()); // 테이블 정보 삭제
		session.invalidate(); //탈퇴했으니 세션 삭제
		return "true";
	}
	
	/**
	 * 관리자 권한으로 회원을 삭제함
	 * @param member_id 회원 아이디
	 * @return ajax로 회원 탈퇴 여부(true/false)를 반환
	 */
	@ResponseBody
	@GetMapping("/deleteMemberByAdmin")
	public String deleteMemberByAdmin(String member_id) {
		memberService.deleteMember(member_id);
		return "true";
	}
	
	/**
	 * 특정 회원 아이디로 회원 정보를 가져오고 회원 정보와 권한을 모델에 바인딩함
	 * @param model 속성부여
	 * @param member_id 회원 아이디
	 * @return 특정 회원 정보 조회 페이지를 리턴
	 */
	@GetMapping("/getMemberByAdmin")
	public String getMemberByAdmin(Model model, String member_id) {
		Member member = memberService.readMemberById(member_id);
		model.addAttribute("thisMember", member);
		model.addAttribute("flag", true);
		return "member/getMember";
	}

	/**
	 * 관리자 권한으로 가입 승인
	 * @param member_id 승인할 member_id
	 */
	@ResponseBody
	@PostMapping("/approval")
	public void approval(String member_id){
		memberService.updateMemberApprovalUsingId(member_id);
	}

	/**
	 * 로그인 후 가입 승인 체크
	 * @param session 해당유저의 세션
	 * @return 검사 결과
	 */
	@PostMapping("/approvalCheck")
	public String approvalCheck(HttpSession session){
		String msg="";
		Member member = (Member) session.getAttribute("member");
		return msg;
	}
	
	/**
	 * 사용 로그에 로그아웃을 기록함
	 */
	@GetMapping("/recordLogout")
	@ResponseBody
	public void recordLogout() {
		memberService.recordLogout();
	}

	/**
	 * 관리자 권한으로 계정 활성화
	 * @param member_id 활성화 할 member_id
	 */
	@ResponseBody
	@PostMapping("/accountActivation")
	public void accountActivation(String member_id){
		memberService.updateAccountActivation(member_id);
	}
}