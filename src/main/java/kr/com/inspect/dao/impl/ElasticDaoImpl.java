package kr.com.inspect.dao.impl;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.com.inspect.dao.ElasticDao;

@Repository
public class ElasticDaoImpl implements ElasticDao {
	@Autowired
	private RestHighLevelClient elasticClient;
	
	/* 자원 회수 */
	@Override
	public void close() {
		try {
			elasticClient.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/* 엘라스틱서치에서 해당되는 인덱스에 있는 데이터 모두 가져오기 */
	@Override
	public SearchHit[] getIndex(String index) {
		// 엘라스틱서치 index 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest().indices(index);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        
        try {
            searchResponse = elasticClient.search(searchRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            System.out.println("search error");
        }
      SearchHit[] searchHits = searchResponse.getHits().getHits();
      return searchHits;
	}
}
