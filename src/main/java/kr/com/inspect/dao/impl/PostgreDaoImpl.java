package kr.com.inspect.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.com.inspect.dto.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.PostgreDao;

/**
 * PostgreSQL DAO
 * @author Yeonhee Kim
 * @author Wooyoung Lee
 * @version 1.0
 *
 */

@Repository
public class PostgreDaoImpl implements PostgreDao {

	/**
	 * DB 연결을 위한 SqlSession
	 */
	@Autowired
	private SqlSession sqlSession;

	/**
	 * Metadata Mapper 네임스페이스
	 */
	private final String metadataNS = "MetadataMapper.";

	/**
	 * Speaker Mapper 네임스페이스
	 */
	private final String speakerNS = "SpeakerMapper.";

	/**
	 * Utterance Mapper 네임스페이스
	 */
	private final String utteranceNS = "UtteranceMapper.";

	/**
	 * EojeolList Mapper 네임스페이스
	 */
	private final String eojeolListNS = "EojeolListMapper.";

	/**
	 * JsonLog Mapper 네임스페이스
	 */
	private final String jsonLogNS = "JsonLogMapper.";

	/**
	 * UtteranceLog Mapper 네임스페이스
	 */
	private final String utteranceLogNS = "UtteranceLogMapper.";

	/**
	 * Metadata 테이블의 총 row 수를 가지고 옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param search_word 검색어
	 * @return Metadata 테이블의 총 row 수
	 */
	public int getMetadataCnt(String data, String search_word) {
		if(search_word == "") { //검색어가 없을 경우
			switch(data) {
				case "all": //전체
					return sqlSession.selectOne(metadataNS+"getMetadataCnt");
				case "korean_lecture": //한국어 강의 데이터
					return sqlSession.selectOne(metadataNS+"getMetadataCntByType", "L");
				case "meeting_audio": //회의 음성 데이터
					return sqlSession.selectOne(metadataNS+"getMetadataCntByType", "C");
				case "customer_reception": //고객 응대 데이터
					return 0;
				case "counsel_audio": //상담 음성 데이터
					return 0;
				default:
					return 0;
			}
		}else { //검색어가 있을 경우
			switch(data) {
				case "all": //전체
					return sqlSession.selectOne(metadataNS+"getToSearchMetdataCnt", search_word);
				case "korean_lecture": //한국어 강의 데이터
					return sqlSession.selectOne(metadataNS+"getToSearchMetdataInLectureCnt", search_word);
				case "meeting_audio": //회의 음성 데이터
					return sqlSession.selectOne(metadataNS+"getToSearchMetdataInMeetingCnt", search_word);
				case "customer_reception": //고객 응대 데이터
					return 0;
				case "counsel_audio": //상담 음성 데이터
					return 0;
				default:
					return 0;
			}
		}		
	}
	
	/**
	 * PostgreSQL에서 Metadata 테이블을 모두 가지고 옴
	 * @return Metadata 테이블 값을 세션값과 함께 리스트에 담아 리턴
	 */
	@Override
	public List<Metadata> getMetadata() {
		return sqlSession.selectList(metadataNS+"getMetadata");
	}
	
	/**
	 * PostgreSQL에서 id로 해당되는 Metadata 테이블을 가져옴
	 * @param id metadata 테이블의 id 값
	 * @return id에 해당하는 metadata 테이블 값을 세션값과 함께 리턴
	 */
	@Override
	public Metadata getMetadataById(Integer id){
		return sqlSession.selectOne(metadataNS+"getMetadataById", id);
	}
	
	/**
	 * metadata의 id로 speaker 리스트를 가져옴
	 * @param metadata_id metadata 테이블의 id 값
	 * @return metadata의 id로 가져온 speaker 리스트
	 */
	@Override
	public List<Speaker> getSpeakerByMetadataId(int metadata_id){
		return sqlSession.selectList(speakerNS+"getSpeakerByMetadataId", metadata_id);
	}
	
	/**
	 * metadata id로 eojeolList 리스트를 가져옴
	 * @param metadata_id metadata 테이블의 id 값
	 * @return metadata id로 가져온 eojeolList 리스트
	 */
	@Override
	public List<EojeolList> getEojeolListByMetadataId(int metadata_id){
		return sqlSession.selectList(eojeolListNS+"getEojeolListByMetadataId", metadata_id);
	}
	
	/**
	 * metadataId로 해당되는 Utterance 테이블을 가져옴
	 * @param metadataId utterance 테이블의 metadataId 값
	 * @return metadataId에 해당하는 utterance 테이블의 값을 세션과 함께 리스트에 담아 리턴
	 */
	@Override
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId) {
		return sqlSession.selectList(utteranceNS+"getUtteranceByMetadataId", metadataId);
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 전체 테이블을 가져옴
	 * @return Metadata 테이블과 Program 테이블을 조인한 전체 테이블
	 */
	@Override
	public List<Metadata> getMetadataAndProgram(){
		List<Metadata> list = sqlSession.selectList(metadataNS+"getMetadataAndProgram");
		return list;
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 검색한 후 페이징 처리하여 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return Metadata 테이블과 Program 테이블을 조인하여 페이징 처리한 테이블
	 */
	@Override
	public List<Metadata> getMetadataAndProgram(String data,
												int limit, 
												int offset,
												String search_word){
		
		List<Metadata> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("offset", offset);
				
		if(search_word == "") { //검색어가 없을 경우
			switch(data) {
				case "all":
					list = sqlSession.selectList(metadataNS+"getMetadataAndProgramByPaging", map);
					break;
				case "korean_lecture":
					list = sqlSession.selectList(metadataNS+"getMetadataAndProgramInLectureByPaging", map);
					break;
				case "meeting_audio":
					list = sqlSession.selectList(metadataNS+"getMetadataAndProgramInMeetingByPaging", map);
					break;
				case "customer_reception":
					break;
				case "counsel_audio":
					break;
				default:
					break;
			}
		}else { //검색어가 있을 경우
			map.put("search_word", search_word);
			switch(data) {
				case "all":
					list = sqlSession.selectList(metadataNS+"searchMetadataAndProgramByPaging", map);
					break;
				case "korean_lecture":
					list = sqlSession.selectList(metadataNS+"searchMetadataAndProgramInLectureByPaging", map);
					break;
				case "meeting_audio":
					list = sqlSession.selectList(metadataNS+"searchMetadataAndProgramInMeetingByPaging", map);
					break;
				case "customer_reception":
					break;
				case "counsel_audio":
					break;
				default:
					break;
			}
		}
		return list;
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 한국어 강의 데이터를 가져옴
	 * @return 조인값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadataAndProgramInLecture(){
		List<Metadata> list = sqlSession.selectList(metadataNS+"getMetadataAndProgramInLecture");
		return list;
	}
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 회의 음성데이터를 가져옴
	 * @return 조인값을 리스트로 담아 리턴
	 */
	public List<Metadata> getMetadataAndProgramInMeeting(){
		List<Metadata> list = sqlSession.selectList(metadataNS+"getMetadataAndProgramInMeeting");
		return list;
	}
	
	/**
	 * metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @param metaId 
	 * @return 조인값을 리턴
	 */
	@Override
	public Metadata getMetadataAndProgramUsingId(Integer metaId){
		Metadata list = sqlSession.selectOne(metadataNS+"getMetadataAndProgramUsingId",metaId);
		return list;
	}

	/**
	 *  utterance_id 를 이용하여 eojeollist 데이터 가져오기
	 *  @param id eojeollist의 utterance_id 값
	 *  @return utterance_id에 해당하는 eojeollist의 테이블 값을 세션값과 함께 리스트에 담아 리턴
	 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id){
		return sqlSession.selectList(eojeolListNS+"getEojeolListUsingUtteranceId", id);
	}

	/**
	 * 검색어를 통해 JsonLog 테이블의 총 row 수를 가져옴
	 * @param search_word 검색어
	 * @return 해당되는 JsonLog 테이블의 총 row 수를 리턴함
	 */
	public int getJsonLogCnt(String search_word) {
		return sqlSession.selectOne(jsonLogNS+"getJsonLogCnt", search_word);
	}
	
	/**
	 * JsonLog 테이블을 검색어를 통해 가져옴
	 * @param limit SELECT할 row의 수
	 * @param offset 몇 번째 row부터 가져올지를 결정
	 * @param search_word 검색어
	 * @return 모든 테이블을 리스트로 담아 리턴
	 */
	public List<JsonLog> getJsonLog(int limit, 
									int offset, 
									String search_word){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("search_word", search_word);
		return sqlSession.selectList(jsonLogNS+"getJsonLog", map);
	}

	/**
	 * utterance id로 해당되는 utterance 튜플을 가져옴
	 * @param id utterance_id 값
	 * @return 튜플값을 세션값과 함께 리턴
	 */
	public Utterance getUtteranceUsingId(String id){
		return sqlSession.selectOne(utteranceNS+"getUtteranceUsingId", id); }

	/**
	 * 대쉬보드에 필요한 회원수, 전사데이터수, 문장수 가져옴
	 * @return 회원수, 전사데이터수, 문장수
	 */
	public Map<String, Object> getDashboardCount(){
		return sqlSession.selectOne(metadataNS + "getDashboardCount"); }

	/**
	 * utterance 수정
	 * @param id 수정할 utterance id
	 * @param form 새로운 문장
	 * @return update된 수
	 */
	public int updateUtteranceForm(String id, String form){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("form", form);
		return sqlSession.update(utteranceNS+"updateUtteranceForm", map);
	}

	/**
	 * utterance id로 어절 삭제
	 * @param utterance_id
	 * @return 삭제된 수
	 */
	public int deleteEojeolByUtteranceId(String utterance_id){
		return sqlSession.delete(eojeolListNS+"deleteEojeolByUtteranceId", utterance_id);
	}

	/**
	 * eojeolList 등록
	 * @param eojeolList 등록할 eojeollist
	 * @return 등록된 수
	 */
	public int insertIntoEojeolList(EojeolList eojeolList){
		return sqlSession.insert(eojeolListNS+"insertIntoEojeolList", eojeolList);
	}

	/**
	 * 검색어와 metadata id를 가지고 총 수를 가져옴
	 * @param metadata_id 가져올 metadata id
	 * @param search_word 검색어
	 * @return 조건에 맞는 데이터 수
	 */
	public int getUtteranceLogCnt(int metadata_id, String search_word){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metadata_id", metadata_id);
		map.put("search_word", search_word);
		return sqlSession.selectOne(utteranceLogNS+"getUtteranceLogCnt", map);
	}

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
											  String search_word){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("metadata_id", metadata_id);
		map.put("search_word", search_word);
		return sqlSession.selectList(utteranceLogNS+"getUtteranceLog", map);
	}

	/**
	 * utteranceLog 데이터를 no로 가져옴
	 * @param no 가져올 데이터의 no
	 * @return utteranceLog
	 */
	public UtteranceLog getUtteranceLogByUsingNo(int no){
		return sqlSession.selectOne(utteranceLogNS+"getUtteranceLogByUsingNo", no);
	}
}