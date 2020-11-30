package kr.com.inspect.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.ResponseData;
import kr.com.inspect.dto.Utterance;

/**
 * PostgreSQL Service Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface PostgreService {
	/**
	 * 엘라스틱 서치에서 받아온 인덱스를 PostgreSQL에 넣음(테스트)
	 * @param index 엘라스틱 서치의 index 값
	 */
	public void insertElasticIndex(String index);
	
	/**
	 * Metadata 테이블을 모두 가지고 옴
	 * @return 
	 */
	public List<Metadata> getMetadata();
	
	/**
	 * id로 해당되는 Metadata 테이블을 가져옴
	 * @param id Metadata 테이블의 id 값
	 * @return
	 */
	public Metadata getMetadataById(Integer id);
	
	/**
	 * metadataId로 해당되는 Utterance 테이블을 가져옴
	 * @param metadataId Utterance 테이블의 metadataId 값
	 * @return
	 */
	public List<Utterance> getUtteranceUsingMetadataId(Integer metadataId);

	/**
	 * utterance id 로 해당되는 utterance 튜플을 가져옴
	 * @param id
	 * @return
	 */
	public Utterance getUtteranceUsingId(String id);
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @param file 파일
	 * @return
	 * @throws Exception 예외 처리
	 */
	public void insertJSONUpload(String path, List<MultipartFile> file) throws Exception;

	/**
	 * 서버 디렉토리 안의 json 파일을 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @return
	 * @throws Exception 예외 처리
	 */
	public String insertJSONDir(String path) throws Exception;
	
	/**
	 * 특정 경로에 있는 xlsx 파일들을 읽어서 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @param file 파일 
	 * @return
	 * @throws Exception 예외 처리
	 */
	public boolean insertXlsxUpload(String path, List<MultipartFile> file) throws Exception;

	/**
	 * 서버 디렉토리 안의 xlsx 파일을 PostgreSQL에 넣음
	 * @param path 파일 디렉토리
	 * @return
	 * @throws Exception 예외 처리
	 */
	public String insertXlsxDir(String path) throws Exception;

	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 전체 테이블을 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @return Metadata 테이블과 Program 테이블을 조인한 전체 테이블
	 */
	public List<Metadata> getMetadataAndProgram(String data);
	
	/**
	 * Metadata 테이블과 Program 테이블을 조인해서 페이징 처리하여 가져옴
	 * @param data 데이터 타입 유형(전체/강의/회의/고객응대/상담)
	 * @param function_name 페이지의 번호를 클릭했을 때 호출되는 자바스크립트 함수명 또는 게시글 조회를 요청하는 함수명을 저장할 변수
	 * @param current_page_no 현재 화면에 출력되고 있는 페이지 번호 또는 페이지의 번호를 클릭했을 때에 번호를 저장할 변수
	 * @return Metadata 테이블과 Program 테이블을 조인하여 페이징 처리한 테이블
	 */
	public ResponseData getMetadataAndProgram(String data, 
											String function_name, 
											int current_page_no,
											int count_per_page,
											int count_per_list);

	/**
	 * metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴
	 * @param metaId 
	 * @return
	 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);

	/**
	 * utterance_id 를 이용하여 eojeollist 데이터 가져오기
	 * @param id eojeollist 테이블의 utterance_id 값
	 * @return
	 */
	public List<EojeolList> getEojeolListUsingUtteranceId(String id);

	/**
	 * JsonLog 테이블을 모두 가져옴
	 * @return
	 */
	public List<JsonLog> getJsonLog();
}
