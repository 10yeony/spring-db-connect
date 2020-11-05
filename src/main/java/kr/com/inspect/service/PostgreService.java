package kr.com.inspect.service;

import java.util.List;

import kr.com.inspect.dto.Metadata;
import kr.com.inspect.dto.Utterance;
import org.apache.ibatis.annotations.Param;

public interface PostgreService {
	/* 엘라스틱 서치에서 받아온 인덱스를 PostgreSQL에 넣음(테스트) */
	public void insertElasticIndex(String index);
	
	/* Metadata 테이블을 모두 가지고 옴 */
	public List<Metadata> getMetadata();
	
	/* id로 해당되는 Metadata 테이블을 가져옴 */
	public Metadata getMetadataById(Integer id);
	
	/* metadataId로 해당되는 Utterance 테이블을 가져옴 */
	public List<Utterance> getUtteranceByMetadataId(Integer metadataId);
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 PostgreSQL에 넣음 */
	public boolean insertJSONObject(String path);
	
	/* 특정 경로에 있는 xlsx 파일들을 읽어서 PostgreSQL에 넣음 */
	public boolean insertXlsxTable(String path);
	
	/* Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public List<Metadata> getMetadataAndProgram();

	/* metadata id로 Metadata 테이블과 Program 테이블을 조인해서 가져옴 */
	public Metadata getMetadataAndProgramUsingId(Integer metaId);
}
