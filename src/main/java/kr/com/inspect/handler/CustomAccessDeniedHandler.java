package kr.com.inspect.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 403 에러(접근 권한 없음) 발생시 처리
 * @author Yeonhee Kim
 * @version 1.0
 */
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * 403 에러(접근 권한 없음) 발생시 에러 메세지를 띄우고 에러 페이지로 이동함
	 * @param request 클라이언트의 요청
	 * @param response 서버에서 보내는 응답
	 * @param accessDeniedException 권한 없음 예외
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		request.setAttribute("errorMsg", "접근 권한이 없습니다.");
		request.getRequestDispatcher("/error").forward(request, response);
	}
	
}
