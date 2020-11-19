package kr.com.inspect.dto;

/**
 * JSON 응답을 위한 객체
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.18 VO 생성(Yeonhee Kim)
 */
public class ResponseData {
	private String code;
	private String status;
	private String message;
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
	
	@Override
	public String toString() {
		return "ResponseData [code=" + code + ", status=" + status + ", message=" + message + ", item=" + item + "]";
	}
}
