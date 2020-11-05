package kr.com.inspect.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import kr.com.inspect.dao.MongoDao;
import kr.com.inspect.parser.JsonParsing;

@Repository
public class MongoDaoImpl implements MongoDao {
	@Autowired
	private MongoClient mongoClient;
	
	private JsonParsing jsonParsing = new JsonParsing();
	
	/* 자원 회수 */
	@Override
	public void close() {
		mongoClient.close();
	}
	
	/* 몽고DB에서 해당되는 database의 collection 객체인 MongoCollection<Document> 만들기 */
	@Override
	public MongoCollection<Document> makeMongoCollection(String database, String col) {
		MongoDatabase DB = mongoClient.getDatabase(database);
		MongoCollection<Document> collection = DB.getCollection(col);
		return collection;
	}
	
	/* 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기 */
	@Override
	public void insertJSONData(String database, String col, String path) {
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		boolean check = false;
		MongoCollection<Document> collection = makeMongoCollection(database, col);
		
		for(File file : fileList){
			/* 확장자가 json인 파일을 읽는다 */
		    if(file.isFile() && FilenameUtils.getExtension(file.getName()).equals("json")){
		    	String fullPath = path + file.getName();
				JSONObject jo = jsonParsing.getJSONObject(fullPath);
				String json = jo.toString();
				Document document = Document.parse(json);
				collection.insertOne(document);
		    }
		}
	}
	
	/* 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기 */
	@Override
	public List<Document> getCollection(String database, String col){
		List<Document> list = new ArrayList<>();
		MongoCollection<Document> collection = makeMongoCollection(database, col);
		FindIterable<Document> documents = collection.find();
		for (Document doc : documents){
			list.add(doc);
        }
		return list;
	}
}
