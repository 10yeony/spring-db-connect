package kr.com.inspect.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * DelegatingFilterProxy를 스프링에 등록. 특별한 설정이 없는 한 별도의 구현이 필요하지 않음.
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.17 스프링 시큐리티 필터 전에 필터 추가하는 메소드 추가(Yeonhee Kim)
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	
	/**
	 * Spring Security 필터 앞에 다른 필터를 추가함(403에러 방지) <br>
	 * 추가된 필터 : MultipartFilter
	 * @param servletContext 서블릿 컨텍스트
	 */
	@Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        insertFilters(servletContext, new MultipartFilter());
    }
}
