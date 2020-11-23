package kr.com.inspect.dao;

import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

/**
 * 몽고 DAO Interface
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

public interface MongoDao {

	/**
	 * 자원 회수
	 */
	public void close();
	
	/**
	 * 해당되는 database의 collection 객체인 MongoCollection<Document> 만들기
	 * @param database DB 값
	 * @param col 컬럼 값
	 * @return DB와 컬럼 값을 컬렉션 형태로 담아 리턴
	 */
	public MongoCollection<Document> makeMongoCollection(String database, String col);
	
	/**
	 * 특정 경로에 있는 JSON 파일들을 읽어서 몽고DB에 넣기
	 * @param database DB 값
	 * @param col 컬럼 값
	 * @param path 경로 값
	 * @return 특정경로의 JSON의 컬럼 값을 몽고DB로 리턴
	 */
	public boolean insertJSONData(String database, String col, String path);
	
	/**
	 * 몽고DB에서 해당되는 database의 collection 데이터를 모두 가져오기
	 * @param database DB 값
	 * @param col 컬럼 값
	 * @return DB 값과 컬럼값을 리스트로 담아 리턴
	 */
	public List<Document> getCollection(String database, String col);
}
