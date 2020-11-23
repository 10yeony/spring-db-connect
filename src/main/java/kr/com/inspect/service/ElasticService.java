package kr.com.inspect.service;

import org.elasticsearch.search.SearchHit;

/**
 * 엘라스틱 서치 Service Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface ElasticService {
	/**
	 * 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기
	 * @param index 엘라스틱 서치 index값
	 * @return index 값을 리턴
	 */
	public SearchHit[] getIndex(String index);
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 엘라스틱서치에 넣기
	 * @param index JSON index 값
	 * @param path 파일 디렉토리
	 * @return 특정 경로의 JSON index값을 리턴
	 */
	public boolean insertJSON(String index, String path);
}
