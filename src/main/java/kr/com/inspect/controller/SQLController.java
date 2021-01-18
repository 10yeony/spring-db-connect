package kr.com.inspect.controller;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Rule;
import kr.com.inspect.dto.RuleLog;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.rule.RunSQL;
import kr.com.inspect.service.RuleService;
import kr.com.inspect.util.ClientInfo;
import kr.com.inspect.util.UsingLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * SQL 실행 관련 Controller
 * @author Wooyoung
 * @version 1.0
 */
@Controller
public class SQLController {
    /**
     * SQL 실행 객체를 필드로 선언
     */
    @Autowired
    private RunSQL runSQL;
    
    @Autowired
    private RuleService ruleService;

    /**
     * 사용자의 사용 로그 기록을 위한 UsingLogUtil 객체
     */
    @Autowired
    private UsingLogUtil usingLogUtil;

    /**
     * 사용자 정보와 관련된 객체
     */
    @Autowired
    private ClientInfo clientInfo;

    /**
     * SQL 실행 페이지로 이동
     * @return SQL 실행 페이지
     */
    @GetMapping("/runSQLPage")
    public String sqlExecutePage(){
        return "postgreSQL/runSQL";
    }

    /**
     * query를 받아서 SQL을 실행
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/runSQL", method = RequestMethod.POST)
    public void runSQL(HttpServletResponse response, @RequestParam("query") String query) {
        ResponseData responseData = new ResponseData(); //ajax 응답 객체

        // 앞뒤 공백 제거, 소문자 전환
        responseData = runSQL.run(responseData, query.trim());

        UsingLog usingLog = new UsingLog();
        usingLog.setContent("SQL 실행 : " + query);
        usingLogUtil.setUsingLog(usingLog);

        responseData.responseJSON(response, responseData);
    }

    /**
     * query를 받아서 sql 쿼리문을 실행하고 그 결과를 DB에 저장
     * @param response 사용자에게 전달할 응답
     * @param rule query문이 담긴 Rule 객체
     */
    @ResponseBody
    @PostMapping("/runRuleSQL")
    public void runRuleSQL(HttpServletResponse response, Rule rule){
        ResponseData responseData = new ResponseData();

        /* 로그인한 사용자 아이디를 가져와서 룰 작성자로 세팅 */
        rule.setCreator(clientInfo.getMemberId());

        responseData = runSQL.run(responseData, rule);
        
        switch(responseData.getCode()) {
	        case "insert" :
	        case "INSERT" :
	        case "update" :
	        case "UPDATE" :
	        case "select" :
	        case "SELECT" :
	        case "delete" :
	        case "DELETE" :
	        case "create" :
	        case "CREATE" :
	        	  Rule vo = ruleService.getRuleBottomLevel(rule.getBottom_level_id());
	            RuleLog ruleLog = new RuleLog();
	            ruleLog.setRule(vo);
	            ruleLog.setContent("룰(SQL) 작성");
	            usingLogUtil.setUsingLog(ruleLog);
	            break;
        }
        responseData.responseJSON(response, responseData);
    }
}
