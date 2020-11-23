package kr.com.inspect.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 엘라스틱서치 JDBC 설정(xml 대체)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Configuration
@PropertySource(value = "classpath:properties/db.properties") 
public class ElasticConfig {
	/*
	 * elasticsearch의 JDBC 정보
	 */
	@Value("${elasticsearch.hostname}") 
	private String hostname;
	
	@Value("${elasticsearch.port}")
	private int port;
	
	@Value("${elasticsearch.scheme}")
	private String scheme;
	
	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(
	            RestClient.builder(
	                    new HttpHost(hostname, port, scheme)));
	}
}
