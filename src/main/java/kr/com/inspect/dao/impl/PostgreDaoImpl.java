package kr.com.inspect.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.PostgreDao;
import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;

/**
 * PostgreSQL DAO
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Repository
public class PostgreDaoImpl implements PostgreDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String metadataNS = "MetadataMapper.";
	private final String utteranceNS = "UtteranceMapper.";
	private final String eojeolListNS = "EojeolListMapper.";
	private final String jsonLogNS = "JsonLogMapper.";
	
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
					return sqlSession.selectOne(metadataNS+"getMetadataCntByType", "LH");
				case "meeting_audio": //회의 음성 데이터
					return sqlSession.selectOne(metadataNS+"getMetadataCntByType", "CG");
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
}