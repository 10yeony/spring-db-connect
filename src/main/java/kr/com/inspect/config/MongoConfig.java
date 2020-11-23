package kr.com.inspect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.mongodb.MongoClient;

/**
 * 몽고 JDBC 설정(xml 대체)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Configuration
@PropertySource(value = "classpath:properties/db.properties")
public class MongoConfig {
	/*
	 * Mongo의 JDBC 정보
	 */
	@Value("${mongoDB.hostname}") 
	private String hostname;
	
	@Value("${mongoDB.port}") 
	private int port;
	
	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(hostname, port);
	}
}
