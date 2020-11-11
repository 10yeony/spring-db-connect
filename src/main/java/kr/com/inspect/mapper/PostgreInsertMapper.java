package kr.com.inspect.mapper;

import kr.com.inspect.dto.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostgreInsertMapper {
	/* PostgreSQL Insert Mapper */
	
	/* 회원가입 */
	@Insert("insert into audio.member"+
			"(member_id, pwd)"+
			"values(#{member_id},#{pwd});")
	public int registerMember(Member member);

	/* program 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.program"+
			"(id, file_num, title, subtitle, running_time)"+
			"VALUES(#{id}, #{file_num}, #{title}, #{subtitle}, #{running_time})")
	public void insertIntoProgram(Program program);
	
	/* metadata 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.metadata"+
			"(creator, annotation_level, year, sampling, title, category, distributor, relation)"+
			"VALUES(#{creator}, #{annotation_level}, #{year}, #{sampling}, #{title}, #{category}, #{distributor}, #{relation});")
	public void insertIntoMetadata(Metadata metadata);
	
	/* speaker 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.speaker"+
			"(no, shortcut, occupation, sex, name, age, metadata_id)"+
			"VALUES(#{no}, #{shortcut}, #{occupation}, #{sex}, #{name}, #{age}, #{metadata_id});")
	public void insertIntoSpeaker(Speaker speaker);
	
	/* utterance 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.utterance"+
			"(id, note, standard_form, form, speaker_no, start, finish, metadata_id)"+
			"VALUES(#{id}, #{note}, #{standard_form}, #{form}, #{speaker_no}, #{start}, #{finish}, #{metadata_id});")
	public void insertIntoUtterance(Utterance utterance);
	
	/* eojeolList 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.eojeolList"+
			"(id, standard, eojeol, finish, isDialect, begin, utterance_id, metadata_id)"+
			"VALUES(#{id}, #{standard}, #{eojeol}, #{finish}, #{isDialect}, #{begin}, #{utterance_id}, #{metadata_id});")
	public void insertIntoEojeolList(EojeolList eojeolList);

	/* JsonLog 테이블에 데이터를 입력함 */
	@Insert("INSERT INTO audio.jsonlog"+
			"(title, start, finish, elapsed)"+
			"VALUES(#{title}, #{start}, #{finish}, #{elapsed});")
	public void insertIntoJsonLog(JsonLog jsonLog);
}
