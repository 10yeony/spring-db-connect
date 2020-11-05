package kr.com.inspect.service;

import org.elasticsearch.search.SearchHit;

public interface ElasticService {
	/* 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기 */
	public SearchHit[] getIndex(String index);
}
