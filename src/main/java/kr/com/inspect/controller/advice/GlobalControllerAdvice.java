package kr.com.inspect.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 글로벌하게 Exception을 처리
 * @author Yeonhee Kim
 *
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	/**
	 * 404 에러와 관련하여 에러 메세지와 함께 error.jsp로 이동함
	 * @param request
	 * @return string 에러 페이지
	 */
	@ExceptionHandler(value = NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNoHandlerFoundException(HttpServletRequest request) {
		request.setAttribute("errorMsg", "존재하지 않는 페이지입니다.");
		return "/error";
    }
	
//	@ExceptionHandler(value = NoHandlerFoundException.class)
//	@ResponseStatus(HttpStatus.FORBIDDEN)
//	public String handleBaseException(HttpServletRequest request) {
//		request.setAttribute("errorMsg", "존재하지 않는 페이지입니다.");
//		return "/error";
//    }

//	@ExceptionHandler(value = Exception.class)
//	public String handleException(HttpServletRequest request, Exception e) {
//		request.setAttribute("errorMsg", "페이지 처리에 예외가 발생하였습니다.");
//		return "/error";
//    }
}
