package kr.com.inspect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.dto.User;
import kr.com.inspect.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/* 회원가입 */
	@ResponseBody
	@RequestMapping(value = "/registerUser", produces = "application/text; charset=utf8")
	public String registerUser(User user, Model model) {
		String msg = "회원가입에 실패하였습니다.";
		int result = userService.registerUser(user);
		if (result == 1) {
			msg = "회원가입 완료! 로그인해주세요.";
		}
		return msg;
	}

	/* 아이디 중복 체크 */
	@ResponseBody
	@PostMapping("/idCheck")
	public String idCheck(HttpServletRequest request) {
		String username = request.getParameter("username");
		int result = userService.idCheck(username);
		return Integer.toString(result);
	}
	
	/* 아이디와 비밀번호로 회원 체크 */
	@ResponseBody
	@PostMapping("/isUser")
	public String isUser(User user, HttpSession session) {
		System.out.println(user);
		try {
			if (session.getAttribute("loginId") != null) {
				session.removeAttribute("loginId");
			}
			User result = userService.loginUser(user);
			session.setAttribute("loginId", result.getUsername());
			String role = result.getUsername();
			if(role.equals("admin")) {
				session.setAttribute("role", "admin");
				return "admin";
			}else {
				session.setAttribute("role", "member");
				return "member";
			}
		} catch (NullPointerException e) {
			return "none";
		}
	}

	/* 로그인 */
	@RequestMapping("/loginUser")
	public String loginUser(HttpSession session) throws Exception {
		if(session.getAttribute("loginId") != null) { //로그인 상태 확인
			return "/main";
		}
		else {
			return "redirect:index.jsp";
		}
	}

	/* 로그아웃 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //모든 세션 초기화
		// session.setAttribute("loginId",null); 으로 해줘도 된다.
		return "redirect:index.jsp";
	}

	/* 회원정보 가져와서 회원 목록 페이지로 이동 */
	@GetMapping("/memberList")
	public String getAllMember() {
		return "member/getMemberList";
	}
}