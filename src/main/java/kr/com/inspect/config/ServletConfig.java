package kr.com.inspect.config;

import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Properties;

/**
 * 각 Servlet에 관한 환경설정(xml 대체)
 * @author Yeonhee Kim, Woo Young
 * @version 1.0
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"kr.com.inspect"})
public class ServletConfig implements WebMvcConfigurer {
	
	/**
	 * 정적 자원 관리
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
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
		mailSender.setUsername("wooyoung.lee@namutech.co.kr");
		mailSender.setPassword("szbkbfhfygcuvamh");
		
		Properties javaMailProperties = new Properties();

		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);

		return mailSender;
	}
}
