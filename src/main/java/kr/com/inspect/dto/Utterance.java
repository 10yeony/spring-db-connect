package kr.com.inspect.dto;

import java.util.List;

/**
 * Utterance 테이블(한 강의당 문장 모음)
 * @author Yeonhee Kim
 * @version 1.0
 */

public class Utterance {
	/**
	 * primary key, 문장을 구분하기 위한 uuid 형식의 아이디
	 */
	private String id;
	
	/**
	 * 노트
	 */
	private String note;
	
	/**
	 * 문장(방언일 경우 standard_form과 다름)
	 */
	private String form;
	
	/**
	 * 표준어 문장
	 */
	private String standard_form;
	
	/**
	 * 방언 문장
	 */
	private String dialect_form;
	
	/**
	 * 발화자 번호
	 */
	private int speaker_no;
	
	/**
	 * 시작 시간
	 */
	private double start;
	
	/**
	 * 끝나는 시간
	 */
	private double finish;
	
	/**
	 * 어절 개수(COUNT 함수로 가져와서 조인함)
	 */
	private int eojeol_count;
	
	/**
	 * foreign key
	 */
	private int metadata_id;
	
	/**
	 * EojeolList 테이블
	 */
	private List<EojeolList> eojoelList;
	
	/**
	 * Speaker 테이블
	 */
	private Speaker speaker;
	
	public Utterance() {}
	public Utterance(String id, String note, String form, String standard_form, String dialect_form, int speaker_no, double start,
			double finish, int eojeol_count, int metadata_id, List<EojeolList> eojoelList, Speaker speaker) {
		super();
		this.id = id;
		this.note = note;
		this.form = form;
		this.standard_form = standard_form;
		this.dialect_form = dialect_form;
		this.speaker_no = speaker_no;
		this.start = start;
		this.finish = finish;
		this.eojeol_count = eojeol_count;
		this.metadata_id = metadata_id;
		this.eojoelList = eojoelList;
		this.speaker = speaker;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getStandard_form() {
		return standard_form;
	}
	public void setStandard_form(String standard_form) {
		this.standard_form = standard_form;
	}
	public String getDialect_form() {
		return dialect_form;
	}
	public void setDialect_form(String dialect_form) {
		this.dialect_form = dialect_form;
	}
	public int getSpeaker_no() {
		return speaker_no;
	}
	public void setSpeaker_no(int speaker_no) {
		this.speaker_no = speaker_no;
	}
	public double getStart() {
		return start;
	}
	public void setStart(double start) {
		this.start = start;
	}
	public double getFinish() {
		return finish;
	}
	public void setFinish(double finish) {
		this.finish = finish;
	}
	public int getEojeol_count() {
		return eojeol_count;
	}
	public void setEojeol_count(int eojeol_count) {
		this.eojeol_count = eojeol_count;
	}
	public int getMetadata_id() {
		return metadata_id;
	}
	public void setMetadata_id(int metadata_id) {
		this.metadata_id = metadata_id;
	}
	public List<EojeolList> getEojoelList() {
		return eojoelList;
	}
	public void setEojoelList(List<EojeolList> eojoelList) {
		this.eojoelList = eojoelList;
	}
	public Speaker getSpeaker() {
		return speaker;
	}
	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}
	
	@Override
	public String toString() {
		return "Utterance [id=" + id + ", note=" + note + ", form=" + form + ", standard_form=" + standard_form
				+ ", dialect_form=" + dialect_form + ", speaker_no=" + speaker_no + ", start=" + start + ", finish="
				+ finish + ", eojeol_count=" + eojeol_count + ", metadata_id=" + metadata_id + ", eojoelList="
				+ eojoelList + ", speaker=" + speaker + "]";
	}
}
