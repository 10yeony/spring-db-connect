package kr.com.inspect.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
/**
 * 로그인 실패시 처리
 * @author Yeonhee Kim
 * @version 1.0, 2020-11-18 클래스 작성(Yeonhee Kim)
 *
 */
public class LoginFailHandler implements AuthenticationFailureHandler {
	
	/**
	 * 인증이 실패했을 경우 실행되는 로직
	 * @param request 사용자로부터 들어온 요청
	 * @param response 서버에서 사용자로 가는 응답
	 * @param exception 인증이 실패한 경우 던져지는 예외
	 * @exception IOExeption 입출력 예외
	 * @exception ServletException 서블릿 예외
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
