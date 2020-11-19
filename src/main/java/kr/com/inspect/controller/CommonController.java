package kr.com.inspect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 공통적인 기능 및 url 설정 컨트롤러
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.18 컨트롤러 생성(Yeonhee Kim)
 */
@Controller
public class CommonController {
	
	/**
	 * 메인페이지로 이동
	 * @return string main 페이지로 리턴
	 */
	@GetMapping("/main")
	public String main() {
		return "/main";
	}
}
