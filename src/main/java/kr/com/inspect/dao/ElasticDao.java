package kr.com.inspect.dao;

import org.elasticsearch.search.SearchHit;

/**
 * 엘라스틱 서치 DAO Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface ElasticDao {

	/**
	 * 자원 회수
	 */
	public void close();
	
	/**
	 * 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기
	 * @param index 엘라스틱 서치의 index 값
	 * @return index값 리턴
	 */
	public SearchHit[] getIndex(String index); 
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 엘라스틱서치에 넣기
	 * @param index JSON 파일의 index 값
	 * @param path JSON 파일 path 값
	 * @return index와 path 값 리턴
	 */
	public boolean insertJSON(String index, String path);
}
