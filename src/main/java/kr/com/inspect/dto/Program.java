package kr.com.inspect.dto;

/**
 * Program 테이블(전체 강의 프로그램 목록)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public class Program {
	/**
	 * 순번
	 */
	private int id;
	/**
	 * 파일이름
	 */
	private String file_num;
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 부제
	 */
	private String subtitle;
	/**
	 * 방송시간
	 */
	private String running_time;
	/**
	 * 이름
	 */
	private String name;
	/**
	 * 성별
	 */
	private String sex;
	
	public Program() {}
	public Program(int id, String file_num, String title, String subtitle, String running_time, String name, String sex) {
		super();
		this.id = id;
		this.file_num = file_num;
		this.title = title;
		this.subtitle = subtitle;
		this.running_time = running_time;
		this.name = name;
		this.sex = sex;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_num() {
		return file_num;
	}
	public void setFile_num(String file_num) {
		this.file_num = file_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getRunning_time() {
		return running_time;
	}
	public void setRunning_time(String running_time) {
		this.running_time = running_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "Program [id=" + id + ", file_num=" + file_num + ", title=" + title + ", subtitle=" + subtitle
				+ ", name=" + name + ", sex=" + sex + ", running_time=" + running_time + "]";
	}
}
