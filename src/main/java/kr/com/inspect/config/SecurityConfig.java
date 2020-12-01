package kr.com.inspect.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import kr.com.inspect.handler.CustomAccessDeniedHandler;
import kr.com.inspect.handler.LoginFailHandler;
import kr.com.inspect.handler.LoginSuccessHandler;
import kr.com.inspect.handler.ProperCookieClearingLogoutHandler;
import kr.com.inspect.service.MemberService;

/**
 * 기본적인 웹 인증에 대한 부분을 구현
 * @author Yeonhee Kim
 * @version 1.0, 2020.11.24 403 에러 핸들링 추가(Yeonhee Kim)
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages= {"kr.com.inspect"})
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	
	/**
	 * 로그인 성공 핸들러 필드 선언
	 */
	@Autowired
	@Qualifier("loginSuccessHandler")
	private LoginSuccessHandler loginSuccessHandler;
	
	/**
	 * 로그인 실패 핸들러 필드 선언
	 */
	@Autowired
	@Qualifier("loginFailHandler")
	private LoginFailHandler loginfailHandler;
	
	/**
	 * 403 에러(접근 권한 없음) 핸들러 필드 선언
	 */
	@Autowired
	@Qualifier("customAccessDeniedHandler")
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	/**
	 * 멤버 서비스 필드 선언
	 */
	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	/**
	 * DB 연동을 위한 DataSource 필드 선언
	 */
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	/**
	 * AuthenticationManagerBuilder에 관련한 설정 적용
	 * @param auth 인증 매니저를 빌드하는 객체
	 * @exception Exception 예외
	 */
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		/* UserDetailsService를 구현하여 커스터마이징한 MemberService 적용 */
		auth.userDetailsService(memberService)
			.passwordEncoder(memberService.passwordEncoder()); 		
	}
	
	/**
	 * Spring Security Filter Chain에 접근하여 url 인증 및 인가 처리 시행
	 * @param http HttpSecurity 객체
	 * @exception Exception 예외
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/* 권한별 접근 페이지 설정 */
		http.authorizeRequests()
			.antMatchers("/", "/login", "/login/**",
							"/register", "/register/**",
							"/sendPwdToEmail").permitAll() //모든 사용자에게 보임
			.antMatchers("/getMemberListByAdmin",
						"/getMemberByAdmin",
						"/getMemberByAdmin/**",
						"/deleteMemberByAdmin",
						"/updateAuthoritiesByAdmin",
						"/getWorkListByAdmin",
						"/editRuleByAdmin",
						"/registerRuleByAdmin",
						"/runRuleByAdmin").access("hasRole('ROLE_ADMIN')") //ADMIN 권한만 접근
			.antMatchers("insertIntoPostgre").access("hasRole('ROLE_INPUT')") //INPUT 권한만 접근
			.antMatchers("/**").access("hasRole('ROLE_VIEW')") //VIEW 권한만 접근
			.anyRequest().authenticated(); //설정되지 않은 모든 url request는 인가된 사용자만 이용
	  
		/* 스프링 시큐리티에서 예외 처리 */
		http.exceptionHandling()
			.accessDeniedHandler(customAccessDeniedHandler); //403 에러 핸들러
		
	  	/* 로그인 폼과 관련된 설정 */
	    http.formLogin()
	    	.loginPage("/login") //커스텀 로그인 페이지
	    	.loginProcessingUrl("/loginProcess") //form의 action과 일치해야 함
	    	.successHandler(loginSuccessHandler)
	    	.failureHandler(loginfailHandler)
	    	.usernameParameter("member_id") //아이디 파라미터 설정
	    	.passwordParameter("pwd"); //비밀번호 파라미터 설정
	    
	    /* 자동 로그인 설정 */
	    http.rememberMe()
	    	.key("inspect") //쿠키에 사용되는 값을 암호화하기 위한 키(key)값
	    	.tokenRepository(persistentTokenRepository()) //DataSource 추가
	    	.tokenValiditySeconds(604800); //토큰 유지 시간(초단위) - 일주일
	    
	    /* 로그아웃 설정 */
	    http.logout()
	    	.logoutUrl("/logout")
	    	.logoutSuccessUrl("/")
	    	.invalidateHttpSession(true); //세션 삭제
	    	//.deleteCookies("JSESSIONID", "remember-me"); //자동 로그인 쿠키, Tomcat이 발급한 세션 유지 쿠키 삭제
	    	//.addLogoutHandler(new ProperCookieClearingLogoutHandler("JESSIONID", "remember-me")); // 사용자 정의 로그아웃핸들러(쿠키 자동삭제 이슈)

	  	/* csrf 공격 방지를 위해 설정 (이 속성을 설정하면, post 요청시 반드시 _csrf token값 넣기) */
	    http.csrf()
	    	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	/**
	 * 특정 url로 접속할 때 인증/인가 처리를 무시
	 * @param web WebSecurity 객체
	 * @exception Exception 예외
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resource/**"); //정적 파일
	}
	
	/**
	 * 자동 로그인을 위한 DataSource 설정을 위해 PersistentTokenRepository를 Bean에 등록하고 리턴함
	 * @return PersistentTokenRepository
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
}