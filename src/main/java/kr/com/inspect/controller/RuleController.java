package kr.com.inspect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RuleController {
	
	@GetMapping("/getRuleListByAdmin")
	public String Rulepage() {
		return "rule/getRuleList";
	}

}
