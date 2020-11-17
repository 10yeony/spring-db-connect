package kr.com.inspect.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
/**
 * 로그인 성공시 처리
 * @author Yeonhee Kim
 * @version 1.0, 2020-11-18 클래스 작성(Yeonhee Kim)
 *
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	/**
	 * 인증이 성공했을 경우 실행되는 로직
	 * @param request 사용자로부터 들어온 요청
	 * @param response 서버에서 사용자로 가는 응답
	 * @param authentication 인증과 관련한 토큰
	 * @exception IOExeption 입출력 예외
	 * @exception ServletException 서블릿 예외
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

}
