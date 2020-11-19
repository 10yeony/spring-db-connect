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

import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 커스텀 로그인 페이지로 이동(반드시 GET 방식이어야 함)
	 * @return string 커스텀 로그인 페이지로 리턴
	 */
	@GetMapping("/login")
	public String Login() {
		return "login";
	}

	/* 회원가입 */
	@ResponseBody
	@RequestMapping(value = "/register", produces = "application/text; charset=utf8")
	public String registerMember(Member member) {
		String msg = "회원가입에 실패하였습니다.";
		int result = memberService.registerMember(member);
		if (result == 2) {
			msg = "회원가입 완료! 로그인해주세요.";
		}
		return msg;
	}

	/* 회원가입시 해당 요소가 DB에 존재하는지 중복 체크 */
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
	
	/* 회원정보를 수정하거나 삭제할 때 비밀번호를 입력받고 자격을 확인함 */
	@ResponseBody
	@PostMapping("/ableToEdit")
	public String ableToEdit(HttpSession session, String pwd) {
		/* 세션의 비밀번호와 사용자가 입력한 비밀번호를 서로 비교하여 boolean 값을 리턴 */
		Member member = (Member) session.getAttribute("member");
		PasswordEncoder pwdEncoder = memberService.passwordEncoder();
		boolean pwdMatch = pwdEncoder.matches(pwd, member.getPassword());
		
		if(pwdMatch) {
			return "true";
		}else {
			return "false";	
		}
	}
	
	/* 회원정보를 수정함 */
	@ResponseBody
	@PostMapping("/updateMember")
	public String UpdateMember(HttpSession session, Member member) {
		int result = memberService.updateMember(member);
		if(result==1) {
			Member vo = memberService.readMemberById(member.getMember_id());
			session.setAttribute("member", vo); //회원정보를 수정했으므로 세션 재설정
			return "true";
		}else {
			return "false";
		}
	}
	
	/* 비밀번호를 수정함 */
	@ResponseBody
	@PostMapping("/updatePwd")
	public String UpdatePwd(HttpSession session, String pwd) {
		Member member = (Member) session.getAttribute("member");
		int result = memberService.updatePwd(member.getMember_id(), pwd);
		if(result == 1) {
			Member vo = memberService.readMemberById(member.getMember_id());
			session.setAttribute("member", vo); //비밀번호를 수정했으므로 세션 재설정
			return "true";
		}else {
			return "false";
		}
	}
	
	/* 회원을 삭제함 */
	@ResponseBody
	@GetMapping("/deleteMember")
	public String deleteMember(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		int result = memberService.deleteMember(member.getMember_id());
		if(result == 2) {
			session.invalidate(); //탈퇴했으니 세션 삭제
			return "true";
		}else {
			return "false";
		}
	}
	
	/* 회원정보 가져와서 회원 목록 페이지로 이동 */
	@GetMapping("/memberList")
	public String getMember(Model model) {
		model.addAttribute("user", memberService.getMember());
		return "member/getMemberList";
	}
}