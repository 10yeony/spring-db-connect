package kr.com.inspect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
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
 * 
 * @author Yeonhee Kim, Woo Young
 * @version 1.0
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class), basePackages = {"kr.com.inspect"})
public class ServletConfig implements WebMvcConfigurer {
	
	/**
	 * 
	 */
	@Value("${report.docx.directory}")
	private String docxPath;
	
	/**
	 * 
	 */
	@Value("${report.xlsx.directory}")
	private String xlsxPath;
	
	/**
	 * 
	 */
	@Value("${report.hwp.directory}")
	private String hwpPath;
	
	/**
	 * 
	 */
	@Value("${report.pptx.directory}")
	private String pptxPath;
	
	/**
	 * 정적 자원 관리
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/reports/hwp/**").addResourceLocations(hwpPath);
		registry.addResourceHandler("/reports/docx/**").addResourceLocations(docxPath);
		registry.addResourceHandler("/reports/xlsx/**").addResourceLocations(xlsxPath);
		registry.addResourceHandler("/reports/pptx/**").addResourceLocations(pptxPath);
		registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
	}
	  
	/**
	 * 뷰 영역 설정
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
	 * @return
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
	 * @return
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
