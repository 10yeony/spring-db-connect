package kr.com.inspect.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.service.PostgreService;
import kr.com.inspect.service.RuleService;

@Controller
public class PagingController {
	
	/**
	 * 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 */
	String function_name = "getTable";
	
	
	@Autowired
	private PostgreService postgreService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private RuleService ruleService;
	
	/**
	 * Metadata & Program 조인해서 가져오기
	 * @param model 속성부여
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 해당 페이지로 값 리턴
	 */
	@GetMapping("/getMetadataAndProgram")
	public String getMetadataAndProgram(Model model, 
										String data, 
										int current_page_no,
										int count_per_page,
										int count_per_list,
										String search_word) {
		
		List<Metadata> metadata = new ArrayList<>();
		ResponseData responseData = new ResponseData();
		if(function_name != null && current_page_no > 0) {
			responseData = postgreService.getMetadataAndProgram(data, 
																function_name, 
																current_page_no, 
																count_per_page, 
																count_per_list,
																search_word);
			Map<String, Object> items = (Map<String, Object>) responseData.getItem();
			metadata = (List<Metadata>) items.get("list");
			model.addAttribute("totalCount", items.get("totalCount"));
			model.addAttribute("pagination",(String)items.get("pagination"));
		} else {
			metadata = postgreService.getMetadataAndProgram(data);
		}
		switch(data) {
			case "all":
				if(search_word == "") {
					model.addAttribute("selectedData", "전체 데이터");
				}else {
					model.addAttribute("selectedData", "전체 데이터 검색 결과");
				}
				break;
			case "korean_lecture":
				if(search_word == "") {
					model.addAttribute("selectedData", "한국어 강의 데이터");
				}else {
					model.addAttribute("selectedData", "한국어 강의 데이터 검색 결과");
				}
				break;
			case "meeting_audio":
				if(search_word == "") {
					model.addAttribute("selectedData", "회의 음성 데이터");
				}else {
					model.addAttribute("selectedData", "회의 음성 데이터 검색 결과");
				}
				break;
			case "customer_reception":
				if(search_word == "") {
					model.addAttribute("selectedData", "고객 응대 데이터");
				}else {
					model.addAttribute("selectedData", "고객 응대 데이터 검색 결과");
				}
				break;
			case "counsel_audio":
				if(search_word == "") {
					model.addAttribute("selectedData", "상담 음성 데이터");
				}else {
					model.addAttribute("selectedData", "상담 음성 데이터 검색 결과");
				}
				break;
			default:
				break;
		}
		model.addAttribute("requestUrl", "getMetadataAndProgram");
		model.addAttribute("result", metadata);
		model.addAttribute("data", data);
		model.addAttribute("count_per_page", count_per_page);
		model.addAttribute("count_per_list", count_per_list);
		model.addAttribute("search_word", search_word);
		return "postgreSQL/getTable";
	}
	
	/**
	 * 검색어로 JsonLog 테이블 페이징 처리하여 가져오기
	 * @param model 속성부여
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 해당 페이지로 값 리턴
	 */
	@GetMapping("/getJsonLog")
	public String getJsonLog(Model model,
									int current_page_no,
									int count_per_page,
									int count_per_list,
									String search_word){
		ResponseData responseData = postgreService.getJsonLog(function_name, 
																	current_page_no,
																	count_per_page,
																	count_per_list,
																	search_word);
		Map<String, Object> items = (Map<String, Object>) responseData.getItem();
		List<JsonLog> list  = (List<JsonLog>) items.get("list");
		model.addAttribute("requestUrl", "getJsonLog");
		model.addAttribute("jsonLog", list);
		model.addAttribute("totalCount", items.get("totalCount"));
		model.addAttribute("pagination",(String)items.get("pagination"));
		model.addAttribute("count_per_page", count_per_page);
		model.addAttribute("count_per_list", count_per_list);
		model.addAttribute("search_word", search_word);
		if(search_word == "") {
			model.addAttribute("selectedData", "전체");
		}else {
			model.addAttribute("selectedData", "검색 결과");
		}
		return "postgreSQL/getJsonLog";
	}
	
	@GetMapping("/getUsingLogList")
	public String getUsingLogList(Model model, 
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word) {
		
		ResponseData responseData = memberService.getUsingLog(function_name, 
																		current_page_no, 
																		count_per_page, 
																		count_per_list,
																		search_word);
		Map<String, Object> items = (Map<String, Object>) responseData.getItem();
		List<UsingLog> usingLog = (List<UsingLog>) items.get("list");
		model.addAttribute("requestUrl", "getUsingLogList");
		model.addAttribute("result", usingLog);
		model.addAttribute("totalCount", items.get("totalCount"));
		model.addAttribute("pagination",(String)items.get("pagination"));
		model.addAttribute("count_per_page", count_per_page);
		model.addAttribute("count_per_list", count_per_list);
		model.addAttribute("search_word", search_word);
		
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("searchResult", value);
		
		return "member/getUsingLogList";
	}
	
	@GetMapping("/getRuleLogList")
	public String getRuleLogList(Model model, 
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word) {
		
		ResponseData responseData = ruleService.getRuleLog(function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word);
		Map<String, Object> items = (Map<String, Object>) responseData.getItem();
		List<RuleLog> ruleLog = (List<RuleLog>) items.get("list");
		model.addAttribute("requestUrl", "getRuleLogList");
		model.addAttribute("result", ruleLog);
		model.addAttribute("totalCount", items.get("totalCount"));
		model.addAttribute("pagination",(String)items.get("pagination"));
		model.addAttribute("count_per_page", count_per_page);
		model.addAttribute("count_per_list", count_per_list);
		model.addAttribute("search_word", search_word);
		
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("searchResult", value);
		
		return "rule/getRuleLogList";
	}
}
