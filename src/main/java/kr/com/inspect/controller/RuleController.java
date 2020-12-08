package kr.com.inspect.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.service.RuleService;

/**
 * 전사규칙에 관한 Controller
 * 
 * @author Yeonhee Kim
 *
 */
@Controller
@RequestMapping("rule")
public class RuleController {

	/**
	 * 전사규칙에 관한 Service
	 */
	@Autowired
	private RuleService ruleService;

	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * 
	 * @param new_top_level_name    대분류 등록을 위한 대분류 이름
	 * @param new_middle_level_name 중분류 등록을 위한 중분류 이름
	 * @param rule                  소분류 등록을 위한 Rule 객체
	 * @return 대분류/중분류/소분류 등록 후 이동할 페이지
	 */
	@PostMapping("/addRuleLevel")
	public String addRuleLevel(Model model, HttpSession session, String new_top_level_name,
			String new_middle_level_name, Rule rule) {

		/* DB 등록 후 row의 수 */
		int result = 0;

		/* 등록 후 화면으로 보여줄 메세지에 포함될 분류 이름 */
		String levelName = "";

		/* 대분류 등록 */
		if (new_top_level_name != null) {
			levelName = "대분류";

			String level = "top";
			Rule vo = new Rule();
			vo.setTop_level_name(new_top_level_name);
			result = ruleService.registerRule(level, vo);
		}

		/* 중분류 등록 */
		else if (new_middle_level_name != null) {
			levelName = "중분류";

			String level = "middle";
			Rule vo = new Rule();
			vo.setTop_level_id(rule.getTop_level_id());
			vo.setMiddle_level_name(new_middle_level_name);
			result = ruleService.registerRule(level, vo);
		}

		/* 소분류 등록 */
		else {
			levelName = "Rule";

			String level = "bottom";
			Member member = (Member) session.getAttribute("member");
			rule.setCreator(member.getMember_id());

			result = ruleService.registerRule(level, rule);

			if (result != 0) {
				model.addAttribute("ruleRegSuccessMsg", levelName + "을 성공적으로 등록하였습니다.");
				return "rule/ruleList";
			}
		}

		if (result == 0) {
			model.addAttribute("ruleRegErrorMsg", "이미 존재하는 " + levelName + "입니다.");
		} else {
			model.addAttribute("ruleRegSuccessMsg", levelName + "를 성공적으로 등록하였습니다.");
		}
		return "rule/registerRule";
	}

	/**
	 * 해당되는 전사규칙 드롭다운 목록을 Ajax로 응답함
	 * 
	 * @param response        HttpServletResponse 객체
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/getRuleCategory")
	@ResponseBody
	public void getRuleCategory(HttpServletResponse response, String top_level_id, String middle_level_id)
			throws IOException {

		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용

		ResponseData responseData = new ResponseData();
		List<Rule> list = ruleService.getRuleCategory(top_level_id, middle_level_id);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("list", list);
		responseData.setItem(items);

		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}

	/**
	 * 해당되는 전사규칙 리스트를 Ajax로 응답함
	 * 
	 * @param response        HttpServletResponse 객체
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/getRuleList")
	@ResponseBody
	public void getRuleList(HttpServletResponse response, String top_level_id, String middle_level_id,
			String bottom_level_id) throws IOException {

		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용

		ResponseData responseData = new ResponseData();
		List<Rule> list = ruleService.getRuleListUsingJoin(top_level_id, middle_level_id, bottom_level_id);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("list", list);
		responseData.setItem(items);

		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}

	@GetMapping("/deleteRuleLevel")
	public String deleteRuleLevel(Model model, String level, int id) {
		String levelName = "";
		switch (level) {
		case "top":
			levelName = "대분류";
			break;
		case "middle":
			levelName = "중분류";
			break;
		case "bottom":
			levelName = "Rule";
			break;
		}

		int result = 0;
		result = ruleService.deleteRule(level, id);
		if (result > 0) {
			model.addAttribute("ruleDelSuccessMsg", levelName + "를 성공적으로 삭제했습니다.");
			if (level.equals("bottom")) {
				model.addAttribute("ruleDelSuccessMsg", levelName + "을 성공적으로 삭제했습니다.");
			}
		} else {
			model.addAttribute("ruleDelErrorMsg", "존재하지 않는 " + levelName + "입니다.");
		}

		if (level.equals("bottom")) {
			return "rule/ruleList";
		}
		return "rule/registerRule";
	}

	@GetMapping("/editRule")
	public String editRulepage(Model model, int bottom_level_id) {
		model.addAttribute("rule", ruleService.getRuleBottomLevel(bottom_level_id));
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

	// @ResponseBody
	// @RequestMapping(value = "/saveRule", produces = "application/text;
	// charset=utf8")
	@PostMapping("/saveRule")
	public String saveRule(Rule rule, Model model) throws Exception {
		Object obj = ruleService.updateContents(rule);
		// String msg = "";

		model.addAttribute("msg", "코드 작성이 완료되었습니다!");
		return "rule/ruleList";
	}
}
