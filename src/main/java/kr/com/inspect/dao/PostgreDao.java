package kr.com.inspect.dao;

import java.util.List;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import org.apache.ibatis.annotations.Param;
import org.json.simple.JSONObject;

import kr.com.inspect.dto.Sound;

public interface PostgreDao {
	/* Metadata 테이블을 모두 가지고 옴 */
	public List<Metadata> getMetadata();
	
	/* id로 해당되는 Metadata 테이블을 가져옴 */
	public Metadata getMetadataById(Integer id);
	
	/* metadataId로 해당되는 Utterance 테이블을 가져옴 */
	public List<Utterance> getUtteranceByMetadataId(Integer metadataId);
	
	/* Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public List<Metadata> getMetadataAndProgram();

	/* metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);
}
