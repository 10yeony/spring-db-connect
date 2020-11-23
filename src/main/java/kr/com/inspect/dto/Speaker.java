package kr.com.inspect.dto;

/**
 * Speaker 테이블(한 강의에서 말하는 발화자 목록)
 * @author Yeonhee Kim
 * @version 1.0
 */

public class Speaker {
	/**
	 * primary key, auto increment
	 */
	private int id; //
	/**
	 * 한 강의에서 발화자 번호
	 */
	private int no; //
	/**
	 * 
	 */
	private int shortcut;
	/**
	 * 직업
	 */
	private String occupation;
	/**
	 * 성별
	 */
	private String sex;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 나이
	 */
	private String age;
	/**
	 * foreign key
	 */
	private int metadata_id;
	
	public Speaker() {}
	public Speaker(int id, int no, int shortcut, String occupation, String sex, String name, String age, int metadata_id) {
		super();
		this.id = id;
		this.no = no;
		this.shortcut = shortcut;
		this.occupation = occupation;
		this.sex = sex;
		this.name = name;
		this.age = age;
		this.metadata_id = metadata_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getShortcut() {
		return shortcut;
	}
	public void setShortcut(int shortcut) {
		this.shortcut = shortcut;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getMetadata_id() {
		return metadata_id;
	}
	public void setMetadata_id(int metadata_id) {
		this.metadata_id = metadata_id;
	}
	
	@Override
	public String toString() {
		return "Speaker [id=" + id + ", no=" + no + ", shortcut=" + shortcut + ", occupation=" + occupation + ", sex="
				+ sex + ", name=" + name + ", age=" + age + ", metadata_id=" + metadata_id + "]";
	}
}
