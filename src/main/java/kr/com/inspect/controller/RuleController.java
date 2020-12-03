package kr.com.inspect.controller;

import javax.servlet.http.HttpSession;


import kr.com.inspect.rule.RuleCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.Rule;

@Controller
public class RuleController {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
	
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
	
	@RequestMapping(value = "/saveRule", method = RequestMethod.POST)
	public String saveRule(Rule rule, Model model) throws Exception{
		
		String contents = rule.getContents();
		model.addAttribute("contents", contents);
		
		//rule.setContents(rule.getContents().replaceAll("\r\n","<br>"));

		RuleCompiler ruleCompiler = new RuleCompiler();
		ruleCompiler.create(contents);
		ruleCompiler.runObject();

		return "rule/confirm";
	}
}
