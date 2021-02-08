package kr.com.inspect.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dao.RuleDao;
import kr.com.inspect.dto.CustomLibrary;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.service.RuleService;
import kr.com.inspect.util.ClientInfo;
import kr.com.inspect.util.UsingLogUtil;

/**
 * 전사규칙에 관한 Controller
 *
 * @author Yeonhee Kim
 * @version 1.0
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
	 * 전사규칙에 관한 Dao
	 */
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 사용자 정보와 관련된 객체
	 */
	@Autowired
	private ClientInfo clientInfo;
	
	/**
	 * 사용자의 사용 로그 기록을 위한 UsingLogUtil 객체
	 */
	@Autowired
	private UsingLogUtil usingLogUtil;

	/**
	 * registerRule 페이지로 이동
	 * @return registerRule 페이지
	 */
	@GetMapping("/registerRule")
	public String registerRulepage() {
		return "rule/registerRule";
	}

	/**
	 * runRule 페이지로 이동
	 * @return runRule 페이지
	 */
	@GetMapping("/runRule")
	public String runRulepage() {
		return "rule/runRule";
	}

	/**
	 * ruleCustom 페이지로 이동
	 * @return ruleCustom 페이지
	 */
	@GetMapping("/customRule")
	public String customRulepage() {
		return "rule/ruleCustom";
	}

	/**
	 * 대분류/중분류/소분류를 DB에 등록함
	 * @param model Model
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
			Rule vo = rule;
			vo.setTop_level_name(new_top_level_name);
			result = ruleService.registerRule(level, vo);
		}

		/* 중분류 등록 */
		else if (new_middle_level_name != null) {
			levelName = "중분류";
			String level = "middle";
			Rule vo = rule;
			vo.setMiddle_level_name(new_middle_level_name);
			result = ruleService.registerRule(level, vo);
		}

		/* 소분류 등록 */
		else {
			levelName = "룰";
			String level = "bottom";

			/* 로그인한 사용자 아이디를 가져와서 룰 작성자로 세팅 */
			rule.setCreator(clientInfo.getMemberId());
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
	 */
	@GetMapping("/getRuleCategory")
	@ResponseBody
	public void getRuleCategory(HttpServletResponse response, String top_level_id, String middle_level_id) {
		ResponseData responseData = new ResponseData();
		List<Rule> list = ruleService.getRuleCategory(top_level_id, middle_level_id);
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("list", list);
		responseData.setItem(items);
		responseData.responseJSON(response, responseData);
	}

	/**
	 * 대분류/중분류/소분류 룰을 삭제함
	 * @param model jsp에 넘겨줄 Model
	 * @param level 대분류/중분류/소분류
	 * @param rule 룰 아이디를 담고 있는 룰 객체
	 * @return 삭제 후 이동할 url
	 */
	@GetMapping("/deleteRuleLevel")
	public String deleteRuleLevel(Model model, String level, Rule rule) {
		String levelName = "";
		switch (level) {
		case "top":
			levelName = "대분류";
			break;
		case "middle":
			levelName = "중분류";
			break;
		case "bottom":
			levelName = "룰";
			break;
		}

		int result = 0;
		result = ruleService.deleteRule(level, rule);
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
	
	/**
	 * 선택한 룰을 삭제함
	 * @param deleteRuleList 삭제할 룰 소분류 아이디 배열
	 * @return 룰 삭제 성공/실패 여부
	 */
	@ResponseBody
	@GetMapping("/deleteCheckedRuleBottomLevel")
	public boolean deleteCheckedRuleBottomLevel(int[] deleteRuleList) {
		String content = "룰 소분류 삭제 - 총 "+deleteRuleList.length+"개";
		final int NO = usingLogUtil.insertUsingLog(content);
		RuleLog ruleLog = new RuleLog();
		ruleLog.setContent(content);
		ruleLog.setUsing_log_no(NO);
		usingLogUtil.setUsingLog(ruleLog);
		int threadCnt = 5;
		ExecutorService executor = Executors.newFixedThreadPool(threadCnt);
		List<Future<?>> futures = new ArrayList<>();
		for(int bottom_level_id : deleteRuleList) {
			futures.add(executor.submit(() -> {
				Rule rule = ruleService.getRuleBottomLevel(bottom_level_id);
				RuleLog ruleLogDetail = new RuleLog();
				ruleLogDetail.setUsing_log_no(NO);
				ruleLogDetail.setContent("룰 소분류 삭제");
				ruleLogDetail.setRule(rule);
				usingLogUtil.insertRuleLogDetail(ruleLogDetail);
				ruleDao.deleteBottomLevel(bottom_level_id);
			}));
		}
		for (Future<?> future : futures) {
			try {
				future.get(); // 스레드 작업이 종료될 때까지 기다림
			} catch (InterruptedException e) {
				// e.printStackTrace();
			} catch (ExecutionException e) {
				// e.printStackTrace();
			}
		}
		executor.shutdownNow(); // Task 종료
		return true;
	}

	/**
	 * 룰을 작성하는 페이지로 이동함
	 * @param model jsp에 넘겨줄 Model
	 * @param bottom_level_id 소분류 아이디
	 * @return 룰 작성 페이지(메서드/SQL) url
	 */
	@GetMapping("/editRule")
	public String editRulepage(Model model, int bottom_level_id) {
		Rule rule = ruleService.getRuleBottomLevel(bottom_level_id);
		model.addAttribute("rule", rule);
		if(rule.getRule_type().equals("method"))
			return "rule/editRule";
		else
			return "rule/editRuleSQL";
	}

	/**
	 * rule을 실행
	 * @param response HttpServletResponse 객체
	 * @param top_level_id 대분류 아이디
	 * @param middle_level_id 중분류 아이디
	 * @param bottom_level_id 소분류 아이디
	 * @throws Exception 예외처리
	 */
	@PostMapping("/runRuleCompiler")
	@ResponseBody
	public void runRuleCompiler(HttpServletResponse response, 
										String top_level_id, 
										String middle_level_id, 
										String bottom_level_id) throws Exception {
		ResponseData responseData = new ResponseData();
		
		/* 해당되는 목록 가져오기 */
		List<Rule> list = ruleService.getRuleList(top_level_id, middle_level_id, bottom_level_id);
		
		/* 컴파일 후 결과값 DB에 저장 */
		ruleService.runRuleCompiler(list);
		
		/* 해당되는 목록 다시 가져오기 */
		list = ruleService.getRuleList(top_level_id, middle_level_id, bottom_level_id);
		
		Map<String, Object> items = new HashMap<String, Object>();
		items.put("list", list);
		responseData.setItem(items);
		responseData.responseJSON(response, responseData);
	}
	
	/**
	 * Ajax 요청이 들어오면 작성한 Rule 코드를 컴파일하고 DB에 등록 후 사용자에게 결과를 보여줌
	 * @param response HttpServletResponse 객체
	 * @param presentVersion 현재 룰 버전(버전을 수정했는지 검사할 때 사용)
	 * @param rule Rule 코드 작성을 위한 Rule 객체
	 * @throws Exception 예외처리
	 */
	@PostMapping("/saveRuleContents")
	@ResponseBody
	public void saveRuleContents(HttpServletResponse response, String presentVersion, Rule rule) throws Exception {
		ResponseData responseData = new ResponseData(); //ajax 응답 객체

		/* 로그인한 사용자 아이디를 가져와서 룰 작성자로 세팅 */
		rule.setCreator(clientInfo.getMemberId());

		/* 컴파일 + DB에 코드 및 결과 업데이트 */
		Map<String, Object> map = ruleService.updateRuleContents(presentVersion, rule);

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
		responseData.responseJSON(response, responseData);
	}
	
	/**
	 * Rule을 작성할 때 참고할 관련 API 문서를 ajax 응답 객체에 담아서 화면에 뿌림
	 * @param response HttpServletResponse
	 * @param class_id DB 상의 클래스 아이디
	 */
	@GetMapping("/getApiDesc")
	@ResponseBody
	public void getApiDesc(HttpServletResponse response, int class_id) {
		ResponseData responseData = new ResponseData(); //ajax 응답 객체
		Map<String, Object> items = ruleService.getApiDesc(class_id);
		responseData.setItem(items);
		responseData.responseJSON(response, responseData);
	}
	
	/**
	 * 커스텀 라이브러리를 서버 스토리지에 업로드하고 DB에 등록함
	 * @param multipartFile 라이브러리 파일(jar, class 파일)
	 * @param pack 패키지명(class 파일인 경우에만 해당)
	 * @return 결과값
	 * @throws Exception 예외
	 */
	@RequestMapping(value = "/uploadCustom", method = RequestMethod.POST)
	@ResponseBody
	public String uploadCustom (@RequestParam("customFile") List<MultipartFile> multipartFile, 
									@RequestParam("pack") String pack) throws Exception{
		ruleService.uploadCustomLibrary(multipartFile, pack);
		return "true";
	}
	
	/**
	 * 사용자가 추가한 라이브러리 목록을 가져옴 
	 * @return 사용자가 추가한 라이브러리 목록
	 */
	@GetMapping("getAllCustomByCreator")
	@ResponseBody
	public List<CustomLibrary> getAllCustomByCreator() {
		String creator = clientInfo.getMemberId();
		return ruleService.getAllCustomLibraryByCreator(creator);
	}
	
	/**
	 * 해당되는 커스텀 라이브러리 파일을 삭제하고 DB에서도 삭제함
	 * @param customLibrary 삭제할 CustomLibrary 객체
	 * @return 성공 여부
	 */
	@PostMapping("deleteCustomLibrary")
	@ResponseBody
	public int deleteCustomLibrary(CustomLibrary customLibrary) {
		String creator = clientInfo.getMemberId();
		customLibrary.setCreator(creator);
		int result = ruleService.deleteCustomLibrary(customLibrary);
		return result;
	}
	
	/**
	 * 룰의 버전별 변화(수정 전/수정 후)를 가져옴
	 * @param model model jsp에 넘겨줄 Model
	 * @param bottom_level_id 룰 소분류 아이디
	 * @param prev_bottom_level_id 룰 버전 관리 목록 아이디
	 * @return 룰의 버전별 변화(수정 전/수정 후)
	 */
	@GetMapping("getRuleChange")
	public String getRuleChange(Model model, int bottom_level_id, int prev_bottom_level_id) {
		List<Rule> list = ruleService.getRuleChange(bottom_level_id, prev_bottom_level_id);
		model.addAttribute("list", list);
		model.addAttribute("size", list.size());
		return "rule/getRuleChange";
	}
}
