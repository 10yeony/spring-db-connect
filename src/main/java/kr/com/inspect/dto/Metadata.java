package kr.com.inspect.dto;

/**
 * Metadata 테이블(한 강의에 대한 전반적인 정보를 담고 있는 테이블)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

import java.util.List;

import kr.com.inspect.paging.CommonDTO;

public class Metadata extends CommonDTO {
	
	/**
	 * 게시글 번호
	 */
	private int row_num;
	
	/**
	 * primary key, auto increment
	 */
	private int id; 
	
	/**
	 * 스크립트 작성자
	 */
	private String creator; 
	
	/**
	 * 
	 */
	private String annotation_level;
	/**
	 * 제작년도
	 */
	private String year; 
	
	/**
	 * 
	 */
	private String sampling;
	/**
	 * 파일이름
	 */
	private String title; 
	
	/**
	 * 
	 */
	private String category;
	
	/**
	 * 파일명에서 앞두글자를 따옴<br>
	 * (강의/회의/상담/고객응대에 따라 파일명 패턴이 다름)
	 */
	private String audio_type;
	
	/**
	 * 
	 */
	private String distributor;
	
	/**
	 * 
	 */
	private String relation;
	
	/**
	 * 문장 개수(COUNT 함수로 가져와서 조인함)
	 */
	private int sentence_count;
	
	/**
	 * 어절 개(COUNT 함수로 가져와서 조인함)
	 */
	private int eojeol_total;
	
	/**
	 * Program 테이블
	 */
	private Program program;
	
	/**
	 * Speaker 테이블
	 */
	private List<Speaker> speaker;
	
	/**
	 * Utterance 테이블
	 */
	private List<Utterance> utterance;

	public Metadata() {
	}

	public Metadata(int row_num, int id, String creator, String annotation_level, String year, String sampling, String title,
			String category, String audio_type, String distributor, String relation, int sentence_count, int eojeol_total, Program program,
			List<Speaker> speaker, List<Utterance> utterance) {
		super();
		this.row_num = row_num;
		this.id = id;
		this.creator = creator;
		this.annotation_level = annotation_level;
		this.year = year;
		this.sampling = sampling;
		this.title = title;
		this.category = category;
		this.audio_type = audio_type;
		this.distributor = distributor;
		this.relation = relation;
		this.sentence_count = sentence_count;
		this.eojeol_total = eojeol_total;
		this.program = program;
		this.speaker = speaker;
		this.utterance = utterance;
	}
	
	public int getRow_num() {
		return row_num;
	}

	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAnnotation_level() {
		return annotation_level;
	}

	public void setAnnotation_level(String annotation_level) {
		this.annotation_level = annotation_level;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSampling() {
		return sampling;
	}

	public void setSampling(String sampling) {
		this.sampling = sampling;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAudio_type() {
		return audio_type;
	}

	public void setAudio_type(String audio_type) {
		this.audio_type = audio_type;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public int getSentence_count() {
		return sentence_count;
	}

	public void setSentence_count(int sentence_count) {
		this.sentence_count = sentence_count;
	}

	public int getEojeol_total() {
		return eojeol_total;
	}

	public void setEojeol_total(int eojeol_total) {
		this.eojeol_total = eojeol_total;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public List<Speaker> getSpeaker() {
		return speaker;
	}

	public void setSpeaker(List<Speaker> speaker) {
		this.speaker = speaker;
	}

	public List<Utterance> getUtterance() {
		return utterance;
	}

	public void setUtterance(List<Utterance> utterance) {
		this.utterance = utterance;
	}

	@Override
	public String toString() {
		return "Metadata [row_num=" + row_num + ", id=" + id + ", creator=" + creator + ", annotation_level="
				+ annotation_level + ", year=" + year + ", sampling=" + sampling + ", title=" + title + ", category="
				+ category + ", audio_type=" + audio_type + ", distributor=" + distributor + ", relation=" + relation
				+ ", sentence_count=" + sentence_count + ", eojeol_total=" + eojeol_total + ", program=" + program
				+ ", speaker=" + speaker + ", utterance=" + utterance + "]";
	}
}
