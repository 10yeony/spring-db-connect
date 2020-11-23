package kr.com.inspect.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PostgreSQL JDBC 설정(xml 대체)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */

@Configuration
@PropertySource(value = "classpath:properties/db.properties")
@EnableTransactionManagement
public class PostgreConfig {
	
	/*
	 * PostgreSQL의 JDBC 정보
	 */
	@Value("${jdbc.driverClassName}") 
	private String driverClassName;

	@Value("${jdbc.url}")
	private String url;

	@Value("${jdbc.username}")
	private String userName;
	
	@Value("${jdbc.password}")
	private String password;

	@Autowired
	private ApplicationContext applicationContext;
	
	/**
	 * 
	 * @return PostgreSQL의 JDBC 정보 반환
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return (DataSource)dataSource;
	}
	
	/**
	 *   
	 * @return PostgreSQL의 DB정보를 mapper 형태로 리턴
	 * @throws Exception 예외처리
	 */
	@Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
       SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
       sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/*.xml"));
       sqlSessionFactoryBean.setDataSource(dataSource());
       return sqlSessionFactoryBean.getObject();
    }
	
    /**
     * 
     * @return 세션템플릿 반환
     * @throws Exception 예외처리
     */
    @Bean
    public SqlSession sqlSession() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate;
    }
}
