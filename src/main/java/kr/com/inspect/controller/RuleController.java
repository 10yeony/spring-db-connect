package kr.com.inspect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RuleController {
	
	@GetMapping("/editRuleByAdmin")
	public String editRulepage() {
		return "rule/editRule";
	}
	
	@GetMapping("/registerRuleByAdmin")
	public String registerRulepage() {
		return "rule/registerRule";
	}
	
	@GetMapping("/runRuleByAdmin")
	public String runRulepage() {
		return "rule/runRule";
	}

}
