package kr.com.inspect.dto;

public class Program {
	private int id;
	private String file_num;
	private String title;
	private String subtitle;
	private String running_time;
	
	public Program() {}
	public Program(int id, String file_num, String title, String subtitle, String running_time) {
		super();
		this.id = id;
		this.file_num = file_num;
		this.title = title;
		this.subtitle = subtitle;
		this.running_time = running_time;
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
	
	@Override
	public String toString() {
		return "Program [id=" + id + ", file_num=" + file_num + ", title=" + title + ", subtitle=" + subtitle
				+ ", running_time=" + running_time + "]";
	}
}
