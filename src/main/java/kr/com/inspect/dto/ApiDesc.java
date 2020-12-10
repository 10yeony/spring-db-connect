package kr.com.inspect.dto;

/**
 * API 문서 정보를 담기 위한 객체
 * @author Yeonhee Kim
 * @version 1.0
 */
public class ApiDesc {
	/**
	 * 클래스 아이디(auto increment)
	 */
	private int class_id;
	
	/**
	 * 클래스 이름 
	 */
	private String class_name;
	
	/**
	 * 클래스 설명
	 */
	private String class_description;
	
	/**
	 * 필드 아이디(auto increment)
	 */
	private int field_id;
	
	/**
	 * 필드 타입
	 */
	private String field_type;
	
	/**
	 * 필드 이름
	 */
	private String field_name;
	
	/**
	 * 필드 설명
	 */
	private String field_description;
	
	/**
	 * 생성자 아이디(auto increment)
	 */
	private int constructor_id;
	
	/**
	 * 생성자 파라미터
	 */
	private String constructor_parameter;
	
	/**
	 * 생성자 설명
	 */
	private String constructor_description;
	
	/**
	 * 메소드 아이디(auto increment)
	 */
	private String method_id;
	
	/**
	 * 메소드 리턴 타입
	 */
	private String method_return_type;
	
	/**
	 * 메소드 이름
	 */
	private String method_name;
	
	/**
	 * 메소드 파라미터
	 */
	private String method_parameter;
	
	/**
	 * 메소드 설명
	 */
	private String method_description;

	public ApiDesc() {}
	public ApiDesc(int class_id, String class_name, String class_description, int field_id, String field_type,
			String field_name, String field_description, int constructor_id, String constructor_parameter,
			String constructor_description, String method_id, String method_return_type, String method_name,
			String method_parameter, String method_description) {
		super();
		this.class_id = class_id;
		this.class_name = class_name;
		this.class_description = class_description;
		this.field_id = field_id;
		this.field_type = field_type;
		this.field_name = field_name;
		this.field_description = field_description;
		this.constructor_id = constructor_id;
		this.constructor_parameter = constructor_parameter;
		this.constructor_description = constructor_description;
		this.method_id = method_id;
		this.method_return_type = method_return_type;
		this.method_name = method_name;
		this.method_parameter = method_parameter;
		this.method_description = method_description;
	}

	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public String getClass_description() {
		return class_description;
	}
	public void setClass_description(String class_description) {
		this.class_description = class_description;
	}
	public int getField_id() {
		return field_id;
	}
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_description() {
		return field_description;
	}
	public void setField_description(String field_description) {
		this.field_description = field_description;
	}
	public int getConstructor_id() {
		return constructor_id;
	}
	public void setConstructor_id(int constructor_id) {
		this.constructor_id = constructor_id;
	}
	public String getConstructor_parameter() {
		return constructor_parameter;
	}
	public void setConstructor_parameter(String constructor_parameter) {
		this.constructor_parameter = constructor_parameter;
	}
	public String getConstructor_description() {
		return constructor_description;
	}
	public void setConstructor_description(String constructor_description) {
		this.constructor_description = constructor_description;
	}
	public String getMethod_id() {
		return method_id;
	}
	public void setMethod_id(String method_id) {
		this.method_id = method_id;
	}
	public String getMethod_return_type() {
		return method_return_type;
	}
	public void setMethod_return_type(String method_return_type) {
		this.method_return_type = method_return_type;
	}
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getMethod_parameter() {
		return method_parameter;
	}
	public void setMethod_parameter(String method_parameter) {
		this.method_parameter = method_parameter;
	}
	public String getMethod_description() {
		return method_description;
	}
	public void setMethod_description(String method_description) {
		this.method_description = method_description;
	}
	
	@Override
	public String toString() {
		return "ApiDesc [class_id=" + class_id + ", class_name=" + class_name + ", class_description="
				+ class_description + ", field_id=" + field_id + ", field_type=" + field_type + ", field_name="
				+ field_name + ", field_description=" + field_description + ", constructor_id=" + constructor_id
				+ ", constructor_parameter=" + constructor_parameter + ", constructor_description="
				+ constructor_description + ", method_id=" + method_id + ", method_return_type=" + method_return_type
				+ ", method_name=" + method_name + ", method_parameter=" + method_parameter + ", method_description="
				+ method_description + "]";
	}
}
