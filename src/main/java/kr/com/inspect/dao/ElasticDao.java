package kr.com.inspect.dao;

import org.elasticsearch.search.SearchHit;

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface ElasticDao {
	/**
	 * 
	 */
	public void close();
	
	/**
	 * 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기
	 * @param index
	 * @return
	 */
	public SearchHit[] getIndex(String index); 
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 엘라스틱서치에 넣기
	 * @param index
	 * @param path
	 * @return
	 */
	public boolean insertJSON(String index, String path);
}
