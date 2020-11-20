package kr.com.inspect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.mongodb.MongoClient;

/**
 * 
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Configuration
@PropertySource(value = "classpath:properties/db.properties")
public class MongoConfig {
	/**
	 * 
	 */
	@Value("${mongoDB.hostname}") 
	private String hostname;
	
	/**
	 * 
	 */
	@Value("${mongoDB.port}") 
	private int port;
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(hostname, port);
	}
}
