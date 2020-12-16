package kr.com.inspect.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dto.CustomRule;
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
	

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * 
	 * @param new_top_level_name    대분류 등록을 위한 대분류 이름
	 * @param new_middle_level_name 중분류 등록을 위한 중분류 이름
	 * @param rule                  소분류 등록을 위한 Rule 객체
	 * @return 대분류/중분류/소분류 등록 후 이동할 페이지
	 */
	@PostMapping("/addRuleLevel")
	public String addRuleLevel(Model model, String new_top_level_name,
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
			
			/* 로그인한 사용자 아이디를 가져와서 룰 작성자로 세팅 */
			Map<String, String> map = getMemberInfo();
			rule.setCreator(map.get("username"));

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
	
	@GetMapping("/customRule")
	public String customRulepage() {
		return "rule/ruleCustom";
	}
	
	@PostMapping("/runRuleCompiler")
	@ResponseBody
	public void runRuleCompiler(HttpServletResponse response, 
										String top_level_id, 
										String middle_level_id, 
										String bottom_level_id) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용
		ResponseData responseData = new ResponseData();
		
		/* 해당되는 목록 가져오기 */
		List<Rule> list = ruleService.getRuleListUsingJoin(top_level_id, middle_level_id, bottom_level_id);
		
		/* 컴파일 후 결과값 DB에 저장 */
		ruleService.runRuleCompiler(list);
		
		/* 해당되는 목록 다시 가져오기 */
		list = ruleService.getRuleListUsingJoin(top_level_id, middle_level_id, bottom_level_id);
		
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("list", list);
		responseData.setItem(items);

		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}

	@GetMapping("/ruleList")
	public String ruleListPage() {
		return "rule/ruleList";
	}
	
	/**
	 * Ajax 요청이 들어오면 작성한 Rule 코드를 컴파일하고 DB에 등록 후 사용자에게 결과를 보여줌
	 * @param rule Rule 코드 작성을 위한 Rule 객체
	 * @throws Exception
	 */
	@PostMapping("/saveRuleContents")
	@ResponseBody
	public void saveRuleContents(HttpServletResponse response, Rule rule) throws Exception {
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용
		ResponseData responseData = new ResponseData(); //ajax 응답 객체

		/* 로그인한 사용자 아이디를 가져와서 룰 작성자로 세팅 */
		String username = getMemberInfo().get("username");
		rule.setCreator(username);

		/* 컴파일 + DB에 코드 및 결과 업데이트 */
		Map<String, Object> map = ruleService.updateContents(rule);

		/* 컴파일 성공 및 실패 처리 */
		if((int)map.get("updateResult") == 0) {
			responseData.setMessage("코드 등록에 실패하였습니다.");
		}else {
			if((boolean)map.get("compile") == true) {
				responseData.setMessage("코드 작성이 완료되었습니다.");
			} else {
				responseData.setMessage("잘못된 코드입니다.");
			}
		}
		Map<String, Object> items = new HashMap<String, Object>();
		Object obj = map.get("object").toString();
		items.put("obj", obj);
		responseData.setItem(items);
		
		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}
	
	/**
	 * Rule을 작성할 때 참고할 관련 API 문서를 ajax 응답 객체에 담아서 화면에 뿌림
	 * @param response HttpServletResponse
	 * @param class_id DB 상의 클래스 아이디 
	 * @throws JsonProcessingException JSON 처리 예외
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/getApiDesc")
	@ResponseBody
	public void getApiDesc(HttpServletResponse response, int class_id) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용
		ResponseData responseData = new ResponseData(); //ajax 응답 객체
		
		Map<String, Object> items = ruleService.getApiDesc(class_id);
		responseData.setItem(items);
		
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().print(mapper.writeValueAsString(responseData));
		response.getWriter().flush();
	}
	
	/**
	 * 스프링 시큐리티에서 로그인한 사용자의 아이디와 암호화된 비밀번호를 가져옴
	 * @return 로그인한 사용자의 아이디와 암호화된 비밀번호
	 */
	public Map<String, String> getMemberInfo(){
		Map<String, String> map = new HashMap<String, String>();
		
		/* 로그인한 사용자 아이디를 가져옴 */
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		UserDetails userDetails = (UserDetails)principal; 
		String username = userDetails.getUsername();
		String password = userDetails.getPassword();
		
		map.put("username", username);
		map.put("password", password);
		return map;
	}
	
	@RequestMapping(value = "/customUpload", method = RequestMethod.POST)
	@ResponseBody
	public String customUpload (@RequestParam("customFile") List<MultipartFile> multipartFile, CustomRule customrule) throws Exception{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		UserDetails userDetails = (UserDetails)principal; 
		String username = userDetails.getUsername();
		System.out.println("시큐리티로 이름 가져오기");
		System.out.println(username);
		

		System.out.println("커스텀룰");
		System.out.println(customrule);
		
		customrule.setCreator(username);
		
		System.out.println("rule creator 설정");
		System.out.println(customrule.getCreator());
		ruleService.customUpload(multipartFile, username, customrule);
		
		System.out.println("서비스 끝");
		System.out.println(multipartFile);
		
		return "true";
	}
}
