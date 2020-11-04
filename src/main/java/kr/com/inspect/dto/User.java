package kr.com.inspect.dto;

public class User {
	
	private String id;
	private String nick;
	private String pw;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public User(String id, String nick, String pw) {
		super();
		this.id = id;
		this.nick = nick;
		this.pw = pw;
	}
	
	
}
