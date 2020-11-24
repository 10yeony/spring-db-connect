package kr.com.inspect.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 각 환경설정의 컨트롤러 (web.xml을 대체)
 * @author Yeonhee Kim
 * @version 1.0
 *
 */
@Configuration
@ComponentScan(basePackages= {"kr.com.inspect"})
public class WebConfig  extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	/**
	 * 각 환경설정 불러오기
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
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletConfig.class };
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	/**
	 * 한글 설정(UTF-8)
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
