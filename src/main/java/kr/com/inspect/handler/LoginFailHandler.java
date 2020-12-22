package kr.com.inspect.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.util.ResponseDataCode;
import kr.com.inspect.util.ResponseDataStatus;

/**
 * 로그인 실패시 처리
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.18 클래스 작성(Yeonhee Kim)
 *
 */

@Component("loginFailHandler")
public class LoginFailHandler implements AuthenticationFailureHandler {

	/**
	 * 인증이 실패했을 경우 실행되는 로직
	 * @param request 사용자로부터 들어온 요청
	 * @param response 서버에서 사용자로 가는 응답
	 * @param exception 인증이 실패한 경우 던져지는 예외
	 * @exception IOException 입출력 예외
	 * @exception ServletException 서블릿 예외
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
				
		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
    	
    	ResponseData responseData = new ResponseData(); //에러 응답 담을 변수 생성
    	responseData.setCode(ResponseDataCode.ERROR); //코드 에러
    	responseData.setStatus(ResponseDataStatus.ERROR); //상태 에러
    	responseData.setMessage("아이디 혹은 비밀번호가 일치하지 않습니다."); //에러 메세지
    	
    	response.setCharacterEncoding("UTF-8"); //UTF-8 인코딩
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
	}
}
