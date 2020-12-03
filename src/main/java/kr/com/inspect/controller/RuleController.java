package kr.com.inspect.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.service.RuleService;

/**
 * 전사규칙에 관한 Controller
 * @author Yeonhee Kim
 *
 */
@Controller
public class RuleController {
	
	/**
	 * 전사규칙에 관한 Service
	 */
	@Autowired
	private RuleService ruleService;
	
	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
	
	/**
	 * 해당되는 전사규칙 드롭다운 목록을 Ajax로 응답함
	 * @param response HttpServletResponse 객체 
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/getRuleCategory")
	@ResponseBody
	public void getRuleCategory(HttpServletResponse response,
										String top_level_id, 
										String middle_level_id) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
		
		ResponseData responseData = new ResponseData();
		List<Rule> list = ruleService.getRuleCategory(top_level_id, middle_level_id);
		Map<String, Object> items = new HashMap<String, Object>();	
    	items.put("list", list);
    	responseData.setItem(items);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
	}
	
	/**
	 * 해당되는 전사규칙 리스트를 Ajax로 응답함
	 * @param response HttpServletResponse 객체
	 * @param top_level_id 전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/getRuleList")
	@ResponseBody
	public void getRuleList(HttpServletResponse response,
								String top_level_id, 
								String middle_level_id,
								String bottom_level_id) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
		
		ResponseData responseData = new ResponseData();
		List<Rule> list = ruleService.getRuleListUsingJoin(top_level_id, 
																	middle_level_id, 
																	bottom_level_id);
		Map<String, Object> items = new HashMap<String, Object>();	
    	items.put("list", list);
    	responseData.setItem(items);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
	}
	
	@GetMapping("/editRule")
	public String editRulepage() {
		return "rule/editRule";
	}
	
	@GetMapping("/registerRule")
	public String registerRulepage() {
		return "rule/registerRule";
	}
	
	@GetMapping("/runRule")
	public String runRulepage() {
		return "rule/runRule";
	}
	
	@GetMapping("/ruleList")
	public String ruleListPage() {
		return "rule/ruleList";
	}
	
	@RequestMapping(value = "/saveRule", method = RequestMethod.POST)
	public String saveRule(Rule rule, Model model) {
		
		System.out.println("POST");
		String contents = rule.getContents();
		model.addAttribute("contents", contents);
		
		//rule.setContents(rule.getContents().replaceAll("\r\n","<br>"));
		System.out.println(rule);
		System.out.println("값 받아오기");
		return "rule/confirm";
	}
}
