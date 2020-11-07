package kr.com.inspect.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Sound;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.User;
import kr.com.inspect.dto.Utterance;

@Mapper
public interface PostgreInsertMapper {
	/* PostgreSQL Insert Mapper */
	
	//회원가입
	@Insert("insert into audio.user"+
			"(userid, pwd)"+
			"values(#{userid},#{pwd});")
	public int insertUser(User user);
	
	/* 테스트용 데이터 입력(엘라스틱서치와의 연동 확인용) */
	@Insert("INSERT INTO public.audiolist"+
			"(id, category,title,company,content)"+
			"VALUES(#{id}, #{category},#{title},#{company},#{content});")
	public void insertTestValue(Sound sound); 

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
			"(id, standard, eojeol, finish, isDialect, begin, utterance_id)"+
			"VALUES(#{id}, #{standard}, #{eojeol}, #{finish}, #{isDialect}, #{begin}, #{utterance_id});")
	public void insertIntoEojeolList(EojeolList eojeolList);
}
