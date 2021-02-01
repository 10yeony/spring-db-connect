package kr.com.inspect.dto;

/**
 * EojeolList 테이블(한 문장을 띄어쓰기 단위로 쪼개낸 어절 모음)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public class EojeolList {
	/**
	 * primary key, auto increment
	 */
	private int no;
	/**
	 * 표준어 어절
	 */
	private String standard;
	/**
	 * 어절(방언일 경우 standard와 다름)
	 */
	private String eojeol;
	/**
	 * 방언이면 true, 아니면 false
	 */
	private boolean isDialect;
	/**
	 * 문장에서 해당 어절이 시작되는 위치
	 */
	private int begin;
	/**
	 * 문장에서 해당 어절이 끝나는 위치
	 */
	private int finish;
	/**
	 * foreign key
	 */
	private String utterance_id;
	/**
	 * foreign key
	 */
	private int metadata_id;
	
	/**
	 * 
	 */
	public EojeolList() {}
	
	public EojeolList(int no, String standard, String eojeol, boolean isDialect, int begin, int finish,
			String utterance_id, int metadata_id) {
		super();
		this.no = no;
		this.standard = standard;
		this.eojeol = eojeol;
		this.isDialect = isDialect;
		this.begin = begin;
		this.finish = finish;
		this.utterance_id = utterance_id;
		this.metadata_id = metadata_id;
	}
	
	/**
	 *
	 * @return
	 */
	public int getNo() {
		return no;
	}

	/**
	 *
	 * @param no
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * 
	 * @return
	 */
	public String getStandard() {
		return standard;
	}
	
	/**
	 * 
	 * @param standard
	 */
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEojeol() {
		return eojeol;
	}
	
	/**
	 * 
	 * @param eojeol
	 */
	public void setEojeol(String eojeol) {
		this.eojeol = eojeol;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDialect() {
		return isDialect;
	}
	
	/**
	 * 
	 * @param isDialect
	 */
	public void setDialect(boolean isDialect) {
		this.isDialect = isDialect;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBegin() {
		return begin;
	}
	
	/**
	 * 
	 * @param begin
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFinish() {
		return finish;
	}
	
	/**
	 * 
	 * @param finish
	 */
	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUtterance_id() {
		return utterance_id;
	}
	
	/**
	 * 
	 * @param utterance_id
	 */
	public void setUtterance_id(String utterance_id) {
		this.utterance_id = utterance_id;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMetadata_id() {
		return metadata_id;
	}
	
	/**
	 * 
	 * @param metadata_id
	 */
	public void setMetadata_id(int metadata_id) {
		this.metadata_id = metadata_id;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "EojeolList [no=" + no + ", standard=" + standard + ", eojeol=" + eojeol + ", isDialect=" + isDialect
				+ ", begin=" + begin + ", finish=" + finish + ", utterance_id=" + utterance_id + ", metadata_id="
				+ metadata_id + "]";
	}
}
