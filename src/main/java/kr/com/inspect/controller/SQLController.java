package kr.com.inspect.controller;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.rule.RunSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
     * @param request 사용자의 입력
     * @param response
     * @throws Exception 예외처리
     */
    @ResponseBody
    @PostMapping("/runSQL")
    public void runSQL(HttpServletResponse response, HttpServletRequest request) throws Exception {
        ResponseData responseData = new ResponseData(); //ajax 응답 객체

        // 앞뒤 공백 제거, 소문자 전환
        String query = request.getParameter("query").toLowerCase().trim();

        responseData = runSQL.run(responseData, query.toLowerCase());
        responseData.responseJSON(response, responseData);
    }
}
