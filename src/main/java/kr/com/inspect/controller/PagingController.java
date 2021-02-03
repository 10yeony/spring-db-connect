package kr.com.inspect.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.com.inspect.dto.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.paging.PagingResponse;
import kr.com.inspect.service.MemberService;
import kr.com.inspect.service.PostgreService;
import kr.com.inspect.service.RuleService;
import kr.com.inspect.util.ClientInfo;

/**
 * 페이징 처리 관련 컨트롤러
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 */

@Controller
public class PagingController {
	
	@Autowired
	ClientInfo clientInfo;
	
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
	public Object addCommonAttribute(boolean isAjax,
									Model model,
									String requestUrl,
									ResponseData responseData,
									int count_per_page,
									int count_per_list,
									String search_word) {
		if(isAjax) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("requestUrl", requestUrl);
			map.put("totalCount", pagingResponse.getTotalCount(responseData));
			map.put("pagination", pagingResponse.getPagination(responseData));
			map.put("result", pagingResponse.getList(responseData));
			map.put("count_per_page", count_per_page);
			map.put("count_per_list", count_per_list);
			map.put("search_word", search_word);
			return map;
		}else {
			model.addAttribute("requestUrl", requestUrl);
			model.addAttribute("totalCount", pagingResponse.getTotalCount(responseData));
			model.addAttribute("pagination", pagingResponse.getPagination(responseData));
			model.addAttribute("result", pagingResponse.getList(responseData));
			model.addAttribute("count_per_page", count_per_page);
			model.addAttribute("count_per_list", count_per_list);
			model.addAttribute("search_word", search_word);
			return model;
		}
	}
	
	/**
	 * 프론트로 보낼 Model에 로그(룰 로그/사용 로그)와 관련된 attribute를 추가함 
	 * @param model Model
	 * @param log_type 로그 타입(사용자 아이디/사용 내역/IP 주소/접속 시간)
	 * @param member_id 사용자 아이디
	 * @param using_list 사용 내역
	 * @param ip_addr IP 주소
	 * @param access_time 접속 시간
	 * @return
	 */
	public Model addLogAttribute(Model model, 
								String log_type, 
								String member_id,
								String using_list,
								String ip_addr,
								String access_time) {
		model.addAttribute("search_logType", log_type);
		model.addAttribute("search_memberId", member_id);
		model.addAttribute("search_usingList", using_list);
		model.addAttribute("search_ipAddr", ip_addr);
		model.addAttribute("search_accessTime", access_time);
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
	 * @return 해당되는 페이지 리턴
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
		addCommonAttribute(false, model, "getMetadataAndProgram", responseData, 
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
	 * 관리자 권한으로 로그인시 사용자 목록을 가져옴
	 * @param model Model
	 * @param data 권한
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @param approval 승인 여부
	 * @return 해당되는 페이지 리턴
	 */
	@GetMapping("/getMemberListByAdmin")
	public String getMemberListByAdmin(Model model, 
										String data,
										int current_page_no,
										int count_per_page,
										int count_per_list,
										String search_word,
										String approval) {
		
		ResponseData responseData = new ResponseData();
		responseData = memberService.getMemberList(data,
												function_name, 
												current_page_no, 
												count_per_page, 
												count_per_list, 
												search_word, 
												approval);
		addCommonAttribute(false, model, "getMemberListByAdmin", responseData, 
						count_per_page, count_per_list, search_word);
		model.addAttribute("data", data);
		model.addAttribute("approval", approval);
		
		switch(data) {
			case "ALL":
				model.addAttribute("selectedRole", "전체 권한");
				break;
			case "ROLE_VIEW":
				model.addAttribute("selectedRole", "데이터 조회 권한");
				break;
			case "ROLE_INPUT":
				model.addAttribute("selectedRole", "데이터 입력 권한");
				break;
			case "ROLE_ADMIN":
				model.addAttribute("selectedRole", "관리자 권한");
				break;
			default:
				break;
		}
		return "member/getMemberList";
	}
	
	/**
	 * JsonLog 테이블을 페이징 처리하여 가져오기
	 * @param model Model
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 해당되는 페이지 리턴
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
		
		addCommonAttribute(false, model, "getJsonLog", responseData, 
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
	 * @param data 사용자 아이디
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @param log_type 로그 타입(사용자 아이디/사용 내역/IP 주소/접속 시간)
	 * @param member_id 사용자 아이디
	 * @param using_list 사용 내역
	 * @param ip_addr IP 주소
	 * @param access_time 접속 시간
	 * @return 해당되는 페이지 리턴
	 */
	@GetMapping("/getUsingLogList")
	public String getUsingLogList(Model model, 
								String data,
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word,
								String log_type,
								String member_id,
								String using_list,
								String ip_addr,
								String access_time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_member_id", member_id);
		map.put("search_using_list", using_list);
		map.put("search_ip_addr", ip_addr);
		map.put("search_access_time", access_time);
		
		ResponseData responseData = memberService.getUsingLog(data,
															function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word,
															log_type,
															map);
		addCommonAttribute(false, model, "getUsingLogList", responseData, 
								count_per_page, count_per_list, search_word);
		addLogAttribute(model, log_type, member_id, using_list, ip_addr, access_time);
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("searchResult", value);
		model.addAttribute("data", data);
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
	 * @param log_type 로그 타입(사용자 아이디/사용 내역/IP 주소/접속 시간)
	 * @param member_id 사용자 아이디
	 * @param using_list 사용 내역
	 * @param ip_addr IP 주소
	 * @param access_time 접속 시간
	 * @return 해당되는 페이지 리턴
	 */
	@GetMapping("/getRuleLogList")
	public String getRuleLogList(Model model, 
								int data,
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word,
								String log_type,
								String member_id,
								String using_list,
								String ip_addr,
								String access_time) {
		if(data > 0) {
			List<RuleLog> ruleLogDetail = ruleService.getAllRuleLogDetailByUsingLogNo(data);
			model.addAttribute("ruleLogDetail", ruleLogDetail);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_member_id", member_id);
		map.put("search_using_list", using_list);
		map.put("search_ip_addr", ip_addr);
		map.put("search_access_time", access_time);
		ResponseData responseData = ruleService.getRuleLog(data,
															function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word,
															log_type,
															map);
		
		addCommonAttribute(false, model, "getRuleLogList", responseData, 
						count_per_page, count_per_list, search_word);
		addLogAttribute(model, log_type, member_id, using_list, ip_addr, access_time);
		
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
	
	/**
	 * 해당되는 룰의 버전 관리 목록을 가져옴
	 * @return 해당되는 룰의 버전 관리 목록 페이지
	 */
	@GetMapping("/rule/getRuleVersionList")
	public String getRuleVersionList(Model model, 
											String data,
											int current_page_no,
											int count_per_page,
											int count_per_list,
											String search_word) {
		ResponseData responseData = ruleService.getPrevRuleVersionList(
															Integer.parseInt(data),
															function_name, 
															current_page_no, 
															count_per_page, 
															count_per_list,
															search_word);
		addCommonAttribute(false, model, "rule/getRuleVersionList", responseData, 
				count_per_page, count_per_list, search_word);
		
		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("data", data);
		model.addAttribute("searchResult", value);
		return "rule/getRuleVersionList";
	}
	
	/**
	 * 룰의 대분류/중분류/소분류 아이디를 저장하고 해당되는 페이지로 이동함
	 * @param model
	 * @param top_level_id 룰 대분류 아이디
	 * @param middle_level_id 룰 중분류 아이디 
	 * @param bottom_level_id 룰 소분류 아이디
	 * @return 해당되는 페이지 리턴
	 */
	@GetMapping("/rule/ruleList/{top_level_id}/{middle_level_id}/{bottom_level_id}")
	public String ruleListPage(Model model, 
							@PathVariable int top_level_id, 
							@PathVariable int middle_level_id, 
							@PathVariable int bottom_level_id) {
		model.addAttribute("top_level_id", top_level_id);
		model.addAttribute("middle_level_id", middle_level_id);
		model.addAttribute("bottom_level_id", bottom_level_id);
		return "rule/ruleList";
	}
	
	/**
	 * 해당되는 전사규칙 리스트를 Ajax로 응답함
	 * @param response        HttpServletResponse 객체
	 * @param top_level_id    전사규칙 대분류 아이디
	 * @param middle_level_id 전사규칙 중분류 아이디
	 * @param bottom_level_id 전사규칙 소분류 아이디
	 * @throws IOException 입출력 예외
	 */
	@GetMapping("/rule/getRuleList")
	@ResponseBody
	public void getRuleList(HttpServletResponse response, 
							String rule_type, 
							String top_level_id, 
							String middle_level_id,
							String bottom_level_id,
							int current_page_no,
							int count_per_page,
							int count_per_list,
							String search_word) {
		ResponseData responseData = new ResponseData();
		responseData = ruleService.getRuleListByPaging(rule_type, 
													top_level_id, 
													middle_level_id, 
													bottom_level_id,
													function_name, 
													current_page_no,
													count_per_page,
													count_per_list,
													search_word);
		Map<String, Object> map = 
				(Map<String, Object>) addCommonAttribute(true, null, "getJsonLog", responseData, 
										count_per_page, count_per_list, search_word);
		responseData.responseJSON(response, map);
	}

	/**
	 * 문장 수정 이력관리를 페이징 처리
	 * @param model
	 * @param data 불러올 metadata id
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @param count_per_page 한 화면에 출력되는 페이지의 수를 저장할 변수
	 * @param count_per_list 한 화면에 출력되는 게시글의 수를 저장할 변수
	 * @param search_word 검색어
	 * @return 문장 수정 이력관리 페이지
	 */
	@GetMapping("/getUtteranceLog")
	public String getUtteranceLog(Model model,
								String data,
								int current_page_no,
								int count_per_page,
								int count_per_list,
								String search_word){
		Metadata metadata = postgreService.getMetadataAndProgramUsingId(Integer.parseInt(data));

		ResponseData responseData = postgreService.getUtteranceLog(
				Integer.parseInt(data),
				function_name,
				current_page_no,
				count_per_page,
				count_per_list,
				search_word);

		addCommonAttribute(false, model, "getUtteranceLog", responseData,
				count_per_page, count_per_list, search_word);

		String value = "";
		if(search_word != "") {
			value = "검색 결과";
		}else {
			value = "전체";
		}
		model.addAttribute("data", data);
		model.addAttribute("searchResult", value);
		model.addAttribute("metadata", metadata);
		return "postgreSQL/utteranceLog";
	}
}
