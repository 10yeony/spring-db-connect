package kr.com.inspect.dto;

/**
 * Metadata 테이블(한 강의에 대한 전반적인 정보를 담고 있는 테이블)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

import java.util.List;

import kr.com.inspect.paging.CommonDto;

public class Metadata extends CommonDto {
	
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
	 * 어절 개수(COUNT 함수로 가져와서 조인함)
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
	
	/**
	 * 
	 * @return
	 */
	public int getRow_num() {
		return row_num;
	}
	
	/**
	 * 
	 * @param row_num
	 */
	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * 
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * 
	 * @return
	 */
	public String getAnnotation_level() {
		return annotation_level;
	}
	/**
	 * 
	 * @param annotation_level
	 */
	public void setAnnotation_level(String annotation_level) {
		this.annotation_level = annotation_level;
	}
	/**
	 * 
	 * @return
	 */
	public String getYear() {
		return year;
	}
	/**
	 * 
	 * @param year
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * 
	 * @return
	 */
	public String getSampling() {
		return sampling;
	}
	/**
	 * 
	 * @param sampling
	 */
	public void setSampling(String sampling) {
		this.sampling = sampling;
	}
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * 
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * 
	 * @return
	 */
	public String getAudio_type() {
		return audio_type;
	}
	/**
	 * 
	 * @param audio_type
	 */
	public void setAudio_type(String audio_type) {
		this.audio_type = audio_type;
	}
	/**
	 * 
	 * @return
	 */
	public String getDistributor() {
		return distributor;
	}
	/**
	 * 
	 * @param distributor
	 */
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	/**
	 * 
	 * @return
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * 
	 * @param relation
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * 
	 * @return
	 */
	public int getSentence_count() {
		return sentence_count;
	}
	/**
	 * 
	 * @param sentence_count
	 */
	public void setSentence_count(int sentence_count) {
		this.sentence_count = sentence_count;
	}
	/**
	 * 
	 * @return
	 */
	public int getEojeol_total() {
		return eojeol_total;
	}
	/**
	 * 
	 * @param eojeol_total
	 */
	public void setEojeol_total(int eojeol_total) {
		this.eojeol_total = eojeol_total;
	}
	/**
	 * 
	 * @return
	 */
	public Program getProgram() {
		return program;
	}
	/**
	 * 
	 * @param program
	 */
	public void setProgram(Program program) {
		this.program = program;
	}
	/**
	 * 
	 * @return
	 */
	public List<Speaker> getSpeaker() {
		return speaker;
	}
	/**
	 * 
	 * @param speaker
	 */
	public void setSpeaker(List<Speaker> speaker) {
		this.speaker = speaker;
	}
	/**
	 * 
	 * @return
	 */
	public List<Utterance> getUtterance() {
		return utterance;
	}
	/**
	 * 
	 * @param utterance
	 */
	public void setUtterance(List<Utterance> utterance) {
		this.utterance = utterance;
	}
	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "Metadata [row_num=" + row_num + ", id=" + id + ", creator=" + creator + ", annotation_level="
				+ annotation_level + ", year=" + year + ", sampling=" + sampling + ", title=" + title + ", category="
				+ category + ", audio_type=" + audio_type + ", distributor=" + distributor + ", relation=" + relation
				+ ", sentence_count=" + sentence_count + ", eojeol_total=" + eojeol_total + ", program=" + program
				+ ", speaker=" + speaker + ", utterance=" + utterance + "]";
	}
}
