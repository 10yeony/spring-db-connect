package kr.com.inspect.dto;

public class Rule {
	private String contents;

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Rule(String contents) {
		super();
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Rule [contents=" + contents + "]";
	}
	
}
