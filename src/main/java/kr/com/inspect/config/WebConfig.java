package kr.com.inspect.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * web.xml의 Java 설정
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
@Configuration
public class WebConfig  extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	/**
	 * ContextLoaderListener가 생성한 애플리케이션 컨텍스트를 설정하는 빈으로<br> 
	 * 데이터 계층이나 백엔드를 구동하는데 사용되는 빈들에 대하여 정의함
	 * @return Configuration 클래스 배열
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { 
							ElasticConfig.class, 
							MongoConfig.class,
							PostgreConfig.class,
							SecurityConfig.class
							};
	}
	
	/**
	 * DispatcherServlet의 애플리케이션 컨텍스트에서 사용되는 빈들에 대하여 정의함
	 * @return Configuration 클래스 배열
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletConfig.class };
	}
	
	/**
	 * 브라우저가 요청한 주소 패턴을 보고 스프링에서 처리할지 말지를 결정
	 * @return 주소 패턴 문자열 배열
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" }; //모든 요청을 처리
	}
	
	/**
	 * 필터 추가 : 한글 설정(UTF-8)
	 * @return Filter 배열
	 */
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}
	
	/**
	 * 404 에러 페이지 처리를 위한 메소드
	 * @return DispatcherServlet
	 */
	@Override
	protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}
}
