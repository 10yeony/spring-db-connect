package kr.com.inspect.service;

import java.util.List;

import kr.com.inspect.dto.EojeolList;
import kr.com.inspect.dto.JsonLog;
import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import org.springframework.web.multipart.MultipartFile;

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
