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
	 * 엘라스틱서치 호스트 이름
	 */
	@Value("${elasticsearch.hostname}") 
	private String hostname;
	
	/**
	 * 엘라스틱서치 포트 번호
	 */
	@Value("${elasticsearch.port}")
	private int port;
	
	/**
	 * 엘라스틱 서치 스키마
	 */
	@Value("${elasticsearch.scheme}")
	private String scheme;
	
	/**
	 * 엘라스틱서치의 호소트 이름, 포트, 스키마가 주입된 RestHighLevelClient 객체를 리턴함
	 * @return RestHighLevelClient
	 */
	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(
	            RestClient.builder(
	                    new HttpHost(hostname, port, scheme)));
	}
}
