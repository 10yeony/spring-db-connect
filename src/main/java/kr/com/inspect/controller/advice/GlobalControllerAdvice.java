//package kr.com.inspect.controller.advice;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
///**
// * 글로벌하게 Exception을 처리
// * @author Yeonhee Kim
// *
// */
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//	/**
//	 * 404 에러와 관련하여 에러 메세지와 함께 error.jsp로 이동함
//	 * @param request 사용자의 요청
//	 * @return string 에러 페이지
//	 */
//	@ExceptionHandler(value = NoHandlerFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public String handleNoHandlerFoundException(HttpServletRequest request) {
//		request.setAttribute("errorMsg", "존재하지 않는 페이지입니다.");
//		return "/error";
//	}
//	/**
//	 * 500 에러와 관련하여 에러 메세지와 함께 error.jsp로 이동함
//	 * @param request 사용자의 요청
//	 * @return string 에러 페이지
//	 */
//	@ExceptionHandler(value = Exception.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public String handleINTERNAL_SERVER_ERROR(HttpServletRequest request) {
//		request.setAttribute("errorMsg", "만료된 페이지입니다.");
//		return "/error";
//	}
//
////	/**
////	 * 따로 지정한 예외 외에 발생한 예외와 관련하여 에러 메세지와 함께 error.jsp로 이동함
////	 * @param request 사용자의 요청
////	 * @param e 발생한 예외
////	 * @return string 에러 페이지
////	 */
////	@ExceptionHandler(value = Exception.class)
////	public String handleDefaultException(HttpServletRequest request, Exception e) {
////		request.setAttribute("errorMsg", "만료된 페이지입니다.");
////		return "/error";
////	}
//}
