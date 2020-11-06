package kr.com.inspect.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Program;
import kr.com.inspect.dto.Speaker;
import kr.com.inspect.dto.User;
import kr.com.inspect.dto.Utterance;

@Mapper
public interface PostgreSelectMapper {
	/* PostgreSQL Select Mapper (간단한 쿼리)
	   - join과 같이 ResultMap이 필요한 경우 : 자바가 아닌 xml에서 작업 */
	
	//로그인
	@Select("SELECT userid, pwd "
				+ "FROM audio.user "
				+ "WHERE userid = #{userid} AND pwd = #{pwd}")
	public User login(User user);
	
	//아이디 중복 체크
	@Select("SELECT COUNT(*) "
				+ "FROM audio.user "
				+ "WHERE userid = #{userid}")
	public int IdCheck(String userid);
	
	/* file_num으로 프로그램 데이터를 받아옴(중복 등록 방지를 위함) */
	@Select("SELECT * "
				+ "FROM audio.program "
				+ "WHERE file_num = #{file_num};")
	public Program getProgramByFileNum(String file_num);
	
	/* creator와 title로 auto increment된 metadata id를 받아옴(다른 테이블의 외래키 입력을 위함) */
	@Select("SELECT id "
				+ "FROM audio.metadata "
				+ "WHERE creator = #{creator} AND title = #{title};")
	public int getMetadataId(Map map); 
	
	/* creator, title로 metadata id가 존재하는지 확인함(중복 등록 방지) */
	@Select("SELECT id "
				+ "FROM audio.metadata "
				+ "WHERE creator = #{creator} AND title = #{title};")
	public String isExistMetadataId(Map map); 

	/* metadata 테이블 가져오기 */
	@Select("SELECT * "
				+ "FROM audio.metadata")
	public List<Metadata> getMetadata();

	/* id를 이용해서 metadata 데이터 가져오기 */
	@Select("SELECT * "
				+ "FROM audio.metadata "
				+ "WHERE id = #{id}")
	public Metadata getMetadataById(Integer id);

	/* utterance_id 를 이용하여 eojeollist 데이터 가져오기 */
	@Select("SELECT * "
				+ "FROM audio.eojeollist "
				+ "WHERE utterance_id = #{id} "
				+ "ORDER BY begin")
	public List<EojeolList> getEojeolListUsingUtteranceId(String id);
}
