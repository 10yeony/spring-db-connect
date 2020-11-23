package kr.com.inspect.dto;

/**
 * JsonLog 테이블(json 파일 저장 시간 정보 테이블)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
public class JsonLog {
	
	/**
	 * primary key
	 */
    private int id;
    /**
     * metadata id
     */
	 private int metadata_id;
	 /**
	  * 파일명
	  */
    private String title;
    /**
     * 파일 저장 시작시간
     */
    private String start;
    /**
     * 파일 저장 종료시간
     */
    private String finish;
    /**
     * 총 걸린 시간
     */
    private String elapsed;
    /**
     * 
     */
    private Program program;

    public JsonLog() {}
    public JsonLog(int id, int metadata_id, String title, String start, String finish, String elapsed, Program program) {
		super();
		this.id = id;
		this.metadata_id = metadata_id;
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.elapsed = elapsed;
		this.program = program;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMetadata_id(){ return metadata_id; }

	public void setMetadata_id(int metadata_id) { this.metadata_id = metadata_id; }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getElapsed() {
		return elapsed;
	}

	public void setElapsed(String elapsed) {
		this.elapsed = elapsed;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
	
	@Override
	public String toString() {
		return "JsonLog [id=" + id + ", metadata_id="+ metadata_id + ", title=" + title + ", start=" + start + ", finish=" + finish + ", elapsed="
				+ elapsed + ", program=" + program + "]";
	}
}
