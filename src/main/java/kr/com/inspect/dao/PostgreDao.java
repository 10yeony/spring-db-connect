package kr.com.inspect.dao;

import java.util.List;
import kr.com.inspect.dto.*;

/**
 * PostgreSQL DAO Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface PostgreDao {
	
	/**
	 * Metadata 테이블을 모두 가지고 옴 
	 * @return Metadata 테이블 값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadata();
	
	/**
	 * id로 해당되는 Metadata 테이블을 가져옴
	 * @param id Metadata의 id 값
	 * @return id에 해당하는 Metadata 테이블 값을 리턴
	 */
	public Metadata getMetadataById(Integer id);
	
	/**
	 * metadataId로 해당되는 Utterance 테이블을 가져옴
	 * @param metadataId Utterance의 metadataId
	 * @return metadataId에 해당하는 Utterance 테이블 값을 리스트로 담아 리턴
	 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId);
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 전체 데이터를 가져옴
	 * @return 조인값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadataAndProgram();
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 한국어 강의 데이터를 가져옴
	 * @return 조인값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadataAndProgramInLecture();
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 회의 음성데이터를 가져옴
	 * @return 조인값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadataAndProgramInMeeting();

	/**
	 * metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @param metaId metadata의 id
	 * @return 조인값을 리턴
	 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);

	/**
	 * utterance_id 를 이용하여 eojeollist 데이터 가져오기
	 * @param id utterance의 id
	 * @return 리스트로 담아 리턴
	 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id);

	/**
	 * JsonLog 테이블을 모두 가져옴
	 * @return 모든 테이블을 리스트로 담아 리턴
	 */
	public List<JsonLog> getJsonLog();

	/**
	 * utterance id로 해당되는 utterance 튜플을 가져옴
	 * @param id utterance의 id
	 * @return 튜플 값 리턴
	 */
	public Utterance getUtteranceUsingId(String id);
}
