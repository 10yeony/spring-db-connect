package kr.com.inspect.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.security.constant.ResponseDataCode;
import kr.com.inspect.security.constant.ResponseDataStatus;

/* 로그인 실패시 핸들러 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		
		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
    	
    	ResponseData responseData = new ResponseData();
    	responseData.setCode(ResponseDataCode.ERROR);
    	responseData.setStatus(ResponseDataStatus.ERROR);
    	responseData.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");

    	
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
	}
}
