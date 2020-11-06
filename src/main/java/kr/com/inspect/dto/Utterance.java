package kr.com.inspect.dto;

import java.util.List;

public class Utterance {
	private String id; //primary key
	private String note;
	private String standard_form;
	private String form;
	private String speaker_no;
	private double start;
	private double end;
	private int eojeol_count;
	private int metadata_id; //foreign key
	private List<EojeolList> eojoelList;
	private List<Speaker> speaker;
	
	public Utterance() {}
	public Utterance(String id, String note, String standard_form, String form, String speaker_no, double start,
			double end, int eojeol_count, int metadata_id, List<EojeolList> eojoelList, List<Speaker> speaker) {
		super();
		this.id = id;
		this.note = note;
		this.standard_form = standard_form;
		this.form = form;
		this.speaker_no = speaker_no;
		this.start = start;
		this.end = end;
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
	public String getStandard_form() {
		return standard_form;
	}
	public void setStandard_form(String standard_form) {
		this.standard_form = standard_form;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getSpeaker_no() {
		return speaker_no;
	}
	public void setSpeaker_no(String speaker_no) {
		this.speaker_no = speaker_no;
	}
	public double getStart() {
		return start;
	}
	public void setStart(double start) {
		this.start = start;
	}
	public double getEnd() {
		return end;
	}
	public void setEnd(double end) {
		this.end = end;
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
	public List<Speaker> getSpeaker() {
		return speaker;
	}
	public void setSpeaker(List<Speaker> speaker) {
		this.speaker = speaker;
	}
	
	@Override
	public String toString() {
		return "Utterance [id=" + id + ", note=" + note + ", standard_form=" + standard_form + ", form=" + form
				+ ", speaker_no=" + speaker_no + ", start=" + start + ", end=" + end + ", eojeol_count=" + eojeol_count
				+ ", metadata_id=" + metadata_id + ", eojoelList=" + eojoelList + ", speaker=" + speaker + "]";
	}
}
