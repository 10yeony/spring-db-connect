package kr.com.inspect.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.inspect.dao.MemberDao;
import kr.com.inspect.dto.Member;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.UsingLog;
import kr.com.inspect.util.ResponseDataCode;
import kr.com.inspect.util.ResponseDataStatus;
import kr.com.inspect.util.UsingLogUtil;

/**
 * 로그인 성공시 처리
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.18 클래스 작성(Yeonhee Kim)
 *
 */

@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private MemberDao memberDao;
	
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
		
		String url = "/main"; //이동할 페이지
		
		/* 세션에 회원 인증 정보를 담음 */
		Member member = (Member)authentication.getPrincipal();
		request.getSession().invalidate(); //세션을 비움
		request.getSession().setAttribute("member", member);
		
		/* 사용자 접속 정보를 DB 사용 로그 테이블에 저장함 */
		String content = "로그인";
		UsingLogUtil usingLogUtil = new UsingLogUtil();
		UsingLog usingLog = usingLogUtil.setUsingLog(content);
		memberDao.insertIntoUsingLog(usingLog);
		
		ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
		
    	ResponseData responseData = new ResponseData();
    	responseData.setCode(ResponseDataCode.SUCCESS); //코드 성공
    	responseData.setStatus(ResponseDataStatus.SUCCESS);//상태 성공
    	Map<String, String> items = new HashMap<String,String>();	
    	items.put("url", url);	// 이동할 페이지 저장
    	responseData.setItem(items);
    	
    	response.setCharacterEncoding("UTF-8");
    	response.setStatus(HttpServletResponse.SC_OK);
    	response.getWriter().print(mapper.writeValueAsString(responseData));
    	response.getWriter().flush();
	}
}
