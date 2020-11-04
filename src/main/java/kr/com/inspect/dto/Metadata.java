package kr.com.inspect.dto;

import java.util.List;

public class Metadata {
	private int id; //primary key, auto increment
	private String creator;
	private String annotation_level;
	private String year;
	private String sampling;
	private String title;
	private String category;
	private String distributor;
	private String relation;
	private List<Program> program;
	private List<Speaker> speaker;
	private List<Utterance> utterance;
	
	public Metadata() {}
	public Metadata(int id, String creator, String annotation_level, String year, String sampling, String title,
			String category, String distributor, String relation, List<Program> program, List<Speaker> speaker,
			List<Utterance> utterance) {
		super();
		this.id = id;
		this.creator = creator;
		this.annotation_level = annotation_level;
		this.year = year;
		this.sampling = sampling;
		this.title = title;
		this.category = category;
		this.distributor = distributor;
		this.relation = relation;
		this.program = program;
		this.speaker = speaker;
		this.utterance = utterance;
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
	public List<Program> getProgram() {
		return program;
	}
	public void setProgram(List<Program> program) {
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
		return "Metadata [id=" + id + ", creator=" + creator + ", annotation_level=" + annotation_level + ", year="
				+ year + ", sampling=" + sampling + ", title=" + title + ", category=" + category + ", distributor="
				+ distributor + ", relation=" + relation + ", program=" + program + ", speaker=" + speaker
				+ ", utterance=" + utterance + "]";
	}
}
