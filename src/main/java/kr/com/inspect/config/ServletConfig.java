package kr.com.inspect.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import kr.com.inspect.service.PostgreService;
import kr.com.inspect.service.impl.PostgreServiceImpl;

/**
 * 각 Servlet에 관한 환경설정(xml 대체)
 * @author Yeonhee Kim, Woo Young
 * @version 1.0
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.com.inspect"}, useDefaultFilters = false, includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class}))
@PropertySource(value = {"classpath:properties/directory.properties", "classpath:properties/sender.properties"})
public class ServletConfig implements WebMvcConfigurer {
	/**
	 * 업로드한 이미지를 보관하는 경로
	 */
	@Value("${user.root.directory}")
	private String userPath;
	
	/**
	 * 발신 이메일
	 */
	@Value("${mail.username}") 
	private String mailUsername;
	
	/**
	 * 발신 이메일 비밀번호
	 */
	@Value("${mail.password}") 
	private String mailPassword;
	
	/**
	 * 정적 자원 관리
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**")
				.addResourceLocations("/resource/");
		registry.addResourceHandler("/user/**")
				.addResourceLocations("file:"+userPath);
	}
	  
	/**
	 * 뷰 영역 Prefix, Suffix 설정
	 */
	public void configureViewResolvers(ViewResolverRegistry registry) {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/views/");
	    bean.setSuffix(".jsp");
	    registry.viewResolver((ViewResolver)bean);
	}
	
	/**
	 * HttpServletRequest를 직접 가져오기 위한 RequestContextListener를 Bean으로 등록
	 * @return RequestContextListener 객체
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}

	/**
	 * 파일업로드 용량, 인코딩 처리 설정
	 * @return CommonsMultipartResolver
	 */
	@Bean(name = {"multipartResolver"})
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(1000000000L);
	    multipartResolver.setDefaultEncoding("UTF-8");
	    return multipartResolver;
	}

	/**
	 * mail 전송 설정
	 * @return JavaMailSender
	 */
	@Bean
	public JavaMailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);
		
		Properties javaMailProperties = new Properties();

		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");
		javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}

	/**
	 *
	 * @return
	 */
	@Bean
	public PostgreService postgreService(){
		PostgreServiceImpl postgreService = new PostgreServiceImpl();
		return postgreService;
	}
}
