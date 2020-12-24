package kr.com.inspect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.paging.PagingResponse;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.service.PostgreService;
import kr.com.inspect.service.RuleService;

@Controller
public class PagingController {
	
	/**
	 * 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 */
	String function_name = "getTable";
	
	/**
	 * PostgreService 필드 선언
	 */
	@Autowired
	private PostgreService postgreService;
	
	/**
	 * MemberService 필드 선언
	 */
	@Autowired
	private MemberService memberService;
	
	/**
	 * RuleService 필드 선언
	 */
	@Autowired
	private RuleService ruleService;
	
	/**
	 * 화면에 응답할 페이징 처리 객체
	 */
	@Autowired
	private PagingResponse pagingResponse;
	
	/**
	 * 프론트로 보낼 Model에 페이징 처리와 관련된 attribute를 추가함 
	 * @param model Model 
	 * @param requestUrl 요청 주소
	 * @param responseData 응답 객체
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어 
	 * @return Model
	 */
	public Model addCommonAttribute(Model model,
									String requestUrl,
									ResponseData responseData,
									int count_per_page,
									int count_per_list,
									String search_word) {
		model.addAttribute("requestUrl", requestUrl);
		model.addAttribute("totalCount", pagingResponse.getTotalCount(responseData));
		model.addAttribute("pagination", pagingResponse.getPagination(responseData));
		model.addAttribute("result", pagingResponse.getList(responseData));
		model.addAttribute("count_per_page", count_per_page);
		model.addAttribute("count_per_list", count_per_list);
		model.addAttribute("search_word", search_word);
		return model;
	}
	
	/**
	 * Metadata & Program 조인해서 가져오기
	 * @param model Model
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
		
		ResponseData responseData = new ResponseData();
		responseData = postgreService.getMetadataAndProgram(data, 
															function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word);
		addCommonAttribute(model, "getMetadataAndProgram", responseData, 
							count_per_page, count_per_list, search_word);
		model.addAttribute("data", data);
		
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
		return "postgreSQL/getTable";
	}
	
	/**
	 * JsonLog 테이블을 페이징 처리하여 가져오기
	 * @param model Model
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
		
		addCommonAttribute(model, "getJsonLog", responseData, 
							count_per_page, count_per_list, search_word);
		if(search_word == "") {
			model.addAttribute("selectedData", "전체");
		}else {
			model.addAttribute("selectedData", "검색 결과");
		}
		return "postgreSQL/getJsonLog";
	}
	
	/**
	 * UsingLog 테이블을 페이징 처리하여 가져오기
	 * @param model Model
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 해당 페이지로 값 리턴
	 */
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
		addCommonAttribute(model, "getUsingLogList", responseData, 
								count_per_page, count_per_list, search_word);
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("searchResult", value);
		return "member/getUsingLogList";
	}
	
	/**
	 * RuleLog 테이블을 페이징 처리하여 가져오기
	 * @param model Model
	 * @param data RuleLog 테이블의 외래키인 using_log_no
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 해당 페이지로 값 리턴
	 */
	@GetMapping("/getRuleLogList")
	public String getRuleLogList(Model model, 
								int data,
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word) {
		
		ResponseData responseData = ruleService.getRuleLog(data,
															function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word);
		
		addCommonAttribute(model, "getRuleLogList", responseData, 
						count_per_page, count_per_list, search_word);
		
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("data", data);
		model.addAttribute("searchResult", value);
		
		return "rule/getRuleLogList";
	}
}
