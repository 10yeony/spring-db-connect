package kr.com.inspect.dto;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 응답을 위한 객체
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.18 VO 생성(Yeonhee Kim)
 */
public class ResponseData {
	/**
	 * 응답 코드
	 */
	private String code;
	/**
	 * 응답 상태
	 */
	private String status;
	/**
	 * 응답 메세지
	 */
	private String message;
	/**
	 * 객체
	 */
	private Object item;
	
	public ResponseData() {}
	public ResponseData(String code, String status, String message, Object item) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
		this.item = item;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getItem() {
		return item;
	}
	public void setItem(Object item) {
		this.item = item;
	}
	
	public void responeJSON(HttpServletResponse response, Map<String, Object> items) {
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용
		
		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			response.getWriter().print(mapper.writeValueAsString(items));
			response.getWriter().flush();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public void responseJSON(HttpServletResponse response, ResponseData responseData) {
		ObjectMapper mapper = new ObjectMapper(); // JSON 변경용
		
		/* 응답시 한글 인코딩 처리 */
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			response.getWriter().print(mapper.writeValueAsString(responseData));
			response.getWriter().flush();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "ResponseData [code=" + code + ", status=" + status + ", message=" + message + ", item=" + item + "]";
	}
}
