package kr.com.inspect.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.security.constant.ResponseDataCode;
import kr.com.inspect.security.constant.ResponseDataStatus;

/* 로그인 성공시 핸들러 */
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
    	
    	ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
    	
    	ResponseData responseData = new ResponseData();
    	responseData.setCode(ResponseDataCode.SUCCESS);
    	responseData.setStatus(ResponseDataStatus.SUCCESS);
    	
    	String prevPage = request.getSession().getAttribute("prevPage").toString(); //이전 페이지 가져오기
    	 
    	Map<String, String> items = new HashMap<String,String>();	
    	items.put("url", prevPage); // 이전 페이지 저장
    	responseData.setItem(items);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
    }
}
