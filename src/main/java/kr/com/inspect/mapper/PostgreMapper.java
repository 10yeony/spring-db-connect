package kr.com.inspect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Sound;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.Utterance;

@Mapper
public interface PostgreMapper {
	
	@Insert("INSERT INTO public.audiolist"+
			"(id, category,title,company,content)"+
			"VALUES(#{id}, #{category},#{title},#{company},#{content});")
	public void insertValue(Sound sound);
	
	@Select("SELECT id FROM audio.user_info WHERE id = #{id};")
	public String readAccount(String id);
	
	@Select("SELECT * FROM audio.program WHERE file_num = #{file_num};")
	public Program getProgramByFileNum(String file_num);
	
	@Select("SELECT id FROM audio.metadata WHERE creator = #{creator} AND title = #{title};")
	public int getMetadataId(Map map); 
	
	@Select("SELECT id FROM audio.metadata WHERE creator = #{creator} AND title = #{title};")
	public String isExistMetadataId(Map map); 
	
	@Insert("INSERT INTO audio.program"+
			"(id, file_num, title, subtitle, running_time)"+
			"VALUES(#{id}, #{file_num}, #{title}, #{subtitle}, #{running_time})")
	public void insertIntoProgram(Program program);
	
	@Insert("INSERT INTO audio.metadata"+
			"(creator, annotation_level, year, sampling, title, category, distributor, relation)"+
			"VALUES(#{creator}, #{annotation_level}, #{year}, #{sampling}, #{title}, #{category}, #{distributor}, #{relation});")
	public void insertIntoMetadata(Metadata metadata);
	
	@Insert("INSERT INTO audio.speaker"+
			"(no, shortcut, occupation, sex, name, age, metadata_id)"+
			"VALUES(#{no}, #{shortcut}, #{occupation}, #{sex}, #{name}, #{age}, #{metadata_id});")
	public void insertIntoSpeaker(Speaker speaker);
	
	@Insert("INSERT INTO audio.utterance"+
			"(id, note, standard_form, form, speaker_no, start, finish, metadata_id)"+
			"VALUES(#{id}, #{note}, #{standard_form}, #{form}, #{speaker_no}, #{start}, #{end}, #{metadata_id});")
	public void insertIntoUtterance(Utterance utterance);
	
	@Insert("INSERT INTO audio.eojeolList"+
			"(id, standard, eojeol, finish, isDialect, begin, utterance_id)"+
			"VALUES(#{id}, #{standard}, #{eojeol}, #{end}, #{isDialect}, #{begin}, #{utterance_id});")
	public void insertIntoEojeolList(EojeolList eojeolList);
	
	@Select("SELECT * FROM audio.metadata")
	public List<Metadata> getTable();

	@Select("SELECT * FROM audio.utterance WHERE id = #{metadataId}")
	public List<Utterance> getUtterance(String metadataId);
}
