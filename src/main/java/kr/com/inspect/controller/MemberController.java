package kr.com.inspect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.dto.Member;
import kr.com.inspect.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	/* 회원가입 */
	@ResponseBody
	@RequestMapping(value = "/register", produces = "application/text; charset=utf8")
	public String registerMember(Member member, Model model) {
		String msg = "회원가입에 실패하였습니다.";
		int result = memberService.registerMember(member);
		if (result == 2) {
			msg = "회원가입 완료! 로그인해주세요.";
		}
		return msg;
	}

	/* 아이디 중복 체크 */
	@ResponseBody
	@PostMapping("register/idCheck")
	public String idCheck(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		int result = memberService.idCheck(member_id);
		return Integer.toString(result);
	}
	
	/* 커스텀 로그인 페이지로 이동 */
	@GetMapping("/login")
	public String Login(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		try {
			/* 이전 페이지 정보 */
			request.getSession().setAttribute("prevPage", savedRequest.getRedirectUrl());
		} catch(NullPointerException e) {
			request.getSession().setAttribute("prevPage", "/");
		}
		return "login";
	}

	/* 로그아웃 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //모든 세션 초기화
		return "redirect:index.jsp";
	}
	
	/* 회원정보 가져와서 회원 목록 페이지로 이동 */
	@GetMapping("/memberList")
	public String getMember() {
		return "member/getMemberList";
	}
}