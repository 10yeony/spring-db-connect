package kr.com.inspect.dao;

import java.util.List;
import kr.com.inspect.dto.*;

public interface PostgreDao {
	
	/**
	 * Metadata 테이블을 모두 가지고 옴 
	 * @return
	 */
	public List<Metadata> getMetadata();
	
	/**
	 * id로 해당되는 Metadata 테이블을 가져옴
	 * @param id
	 * @return
	 */
	public Metadata getMetadataById(Integer id);
	
	/**
	 * metadataId로 해당되는 Utterance 테이블을 가져옴
	 * @param metadataId
	 * @return
	 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId);
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @return
	 */
	public List<Metadata> getMetadataAndProgram();

	/**
	 * metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @param metaId
	 * @return
	 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);

	/**
	 * utterance_id 를 이용하여 eojeollist 데이터 가져오기
	 * @param id
	 * @return
	 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id);

	/**
	 * JsonLog 테이블을 모두 가져옴
	 * @return
	 */
	public List<JsonLog> getJsonLog();

	/**
	 * utterance id로 해당되는 utterance 튜플을 가져옴
	 * @param id
	 * @return
	 */
	public Utterance getUtteranceUsingId(String id);
}
