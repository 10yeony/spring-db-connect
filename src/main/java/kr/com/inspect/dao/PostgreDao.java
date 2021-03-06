package kr.com.inspect.dao;

import java.util.List;
import java.util.Map;

import kr.com.inspect.dto.*;

/**
 * PostgreSQL DAO Interface
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 *
 */
public interface PostgreDao {
	
	/**
	 * Metadata 테이블의 총 row 수를 가지고 옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param search_word 검색어
	 * @return Metadata 테이블의 총 row 수
	 */
	public int getMetadataCnt(String data, String search_word);
	
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
	 * metadata id로 speaker 리스트를 가져옴
	 * @param metadata_id metadata 테이블의 id 값
	 * @return metadata id로 가져온 speaker 리스트
	 */
	public List<Speaker> getSpeakerByMetadataId(int metadata_id);
	
	/**
	 * metadata id로 eojeolList 리스트를 가져옴
	 * @param metadata_id metadata 테이블의 id 값
	 * @return metadata id로 가져온 eojeolList 리스트
	 */
	public List<EojeolList> getEojeolListByMetadataId(int metadata_id);
	
	/**
	 * metadataId로 해당되는 Utterance 테이블을 가져옴
	 * @param metadataId Utterance의 metadataId
	 * @return metadataId에 해당하는 Utterance 테이블 값을 리스트로 담아 리턴
	 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId);
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 전체 테이블을 가져옴
	 * @return Metadata 테이블과 Program 테이블을 조인한 전체 테이블
	 */
	public List<Metadata> getMetadataAndProgram();
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 페이징 처리하여 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return Metadata 테이블과 Program 테이블을 조인하여 페이징 처리한 전체 테이블
	 */
	public List<Metadata> getMetadataAndProgram(String data,
												int limit, 
												int offset,
												String search_word);
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 한국어 강의 데이터를 모두 가져옴
	 * @return Metadata 테이블과 Program 테이블을 조인한 한국어 강의 데이터 테이블
	 */
	public List<Metadata> getMetadataAndProgramInLecture();
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 회의 음성데이터를 모두 가져옴
	 * @return Metadata 테이블과 Program 테이블을 조인한 회의 음성데이터 테이블
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
	 * 검색어를 통해 JsonLog 테이블의 총 row 수를 가져옴
	 * @param search_word 검색어
	 * @return 해당되는 JsonLog 테이블의 총 row 수를 리턴함
	 */
	public int getJsonLogCnt(String search_word);

	/**
	 * JsonLog 테이블을 검색어를 통해 가져옴
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 모든 테이블을 리스트로 담아 리턴
	 */
	public List<JsonLog> getJsonLog(int limit, 
									int offset, 
									String search_word);

	/**
	 * utterance id로 해당되는 utterance 튜플을 가져옴
	 * @param id utterance의 id
	 * @return 튜플 값 리턴
	 */
	public Utterance getUtteranceUsingId(String id);

	/**
	 * 대쉬보드에 필요한 회원수, 전사데이터수, 문장수 가져옴
	 * @return 회원수, 전사데이터수, 문장수
	 */
	public Map<String, Object> getDashboardCount();

	/**
	 * utterance 수정
	 * @param id 수정할 utterance id
	 * @param form 새로운 문장
	 * @return update된 수
	 */
	public int updateUtteranceForm(String id, String form);

	/**
	 * utterance id로 어절 삭제
	 * @param id 삭제할 utterance id
	 * @return 삭제된 수
	 */
	public int deleteEojeolByUtteranceId(String id);

	/**
	 * eojeolList 등록
	 * @param eojeolList 등록할 eojeollist
	 * @return 등록된 수
	 */
	public int insertIntoEojeolList(EojeolList eojeolList);

	/**
	 * 검색어와 metadata id를 가지고 총 수를 가져옴
	 * @param metadata_id 가져올 metadata id
	 * @param search_word 검색어
	 * @return 조건에 맞는 데이터 수
	 */
	public int getUtteranceLogCnt(int metadata_id, String search_word);

	/**
	 * 검색어를 가지고 페이징 처리
	 * @param metadata_id 해당되는 metadata id
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 해당되는 리스트
	 */
	public List<UtteranceLog> getUtteranceLog(int metadata_id,
										 int limit,
										 int offset,
										  String search_word);

	/**
	 * utteranceLog 데이터를 no로 가져옴
	 * @param no 가져올 데이터의 no
	 * @return utteranceLog
	 */
	public UtteranceLog getUtteranceLogByUsingNo(int no);
}
