package kr.com.inspect.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import kr.com.inspect.dao.ElasticDao;
import kr.com.inspect.dao.MongoDao;
import kr.com.inspect.parser.JsonParsing;

@Repository
@SuppressWarnings("deprecation")
public class MongoDaoImpl implements MongoDao {
	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private ElasticDao elasticDao;
	
	/* 자원 회수 */
	@Override
	public void close() {
		mongoClient.close();
	}
	
	/* 몽고DB에 엘라스틱서치에서 받아온 인덱스 데이터를 입력하기 */
	@Override
	public void insertElasticIndex(String database, String col, String index) {
		// 인덱스를 통해 엘라스틱서치에서 데이터를 받아옴
		SearchHit[] searchHits = elasticDao.getIndex(index);
		
		MongoDatabase DB = mongoClient.getDatabase(database);
		MongoCollection<Document> collection = DB.getCollection(col);
		
		for(SearchHit hit: searchHits) {
			String json = hit.getSourceAsString();
			Document document = Document.parse(json);
			//document.put("_id", hit.getId());
			collection.insertOne(document);
		}
	}
	
	/* 몽고DB에 JSON 데이터를 입력하기 */
	@Override
	public void insertJSONData(String database, String col, String fullPath) {
		MongoDatabase DB = mongoClient.getDatabase(database);
		MongoCollection<Document> collection = DB.getCollection(col);
		
		JsonParsing jsonParsing = new JsonParsing();
        JSONObject jo = jsonParsing.getJSONObject(fullPath);
        String json = jo.toString();
        Document document = Document.parse(json);
        collection.insertOne(document);
	}
	
	/* 몽고DB 해당 컬렉션 다 가져오기 */
	@Override
	public List<Document> getCollection(String database, String col){
		List<Document> list = new ArrayList<>();
		MongoDatabase mDB = mongoClient.getDatabase(database);
		MongoCollection<Document> mCollection = mDB.getCollection(col);
		FindIterable<Document> documents = mCollection.find();
		for (Document doc : documents){
			list.add(doc);
        }
		return list;
	}
}
