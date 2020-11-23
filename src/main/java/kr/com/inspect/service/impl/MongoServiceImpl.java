package kr.com.inspect.service.impl;

import java.util.List;

import org.bson.Document;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.MongoDao;
import kr.com.inspect.service.MongoService;

/**
 * 몽고 Service
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Service
public class MongoServiceImpl implements MongoService {
	/**
	 * 엘라스틱 서치 dao 필드 선언
	 */
	@Autowired
	private ElasticDao elasticDao;
	
	/**
	 * 몽고 dao 필드 선언
	 */
	@Autowired
	private MongoDao mongoDao;
	
	/**
	 * 몽고DB 컬렉션에 엘라스틱서치에서 받아온 인덱스 데이터를 입력하기
	 * @param database DB 값
	 * @param col 컬럼 값
	 * @param index 엘라스틱서치의 index 값
	 * @return
	 */
	@Override
	public void insertElasticIndex(String database, String col, String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);
		MongoCollection<Document> collection = mongoDao.makeMongoCollection(database, col);
		
		for(SearchHit hit: searchHits) {
			String json = hit.getSourceAsString();
			Document document = Document.parse(json);
			//document.put("_id", hit.getId());
			collection.insertOne(document);
		}
	}
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 
	 * @param database
	 * @param col
	 * @param fullPath
	 * @return
	 */
	@Override
	public boolean insertJSONData(String database, String col, String fullPath) {
		return mongoDao.insertJSONData(database, col, fullPath);
	}
	
	/**
	 * 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기
	 * @param database
	 * @param col
	 * @return
	 */
	public List<Document> getCollection(String database, String col){
		return mongoDao.getCollection(database, col);
	}
}
