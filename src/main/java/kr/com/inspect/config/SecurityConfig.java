package kr.com.inspect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import kr.com.inspect.security.CustomAuthenticationFailureHandler;
import kr.com.inspect.security.CustomAuthenticationSuccessHandler;
import kr.com.inspect.service.MemberService;

/* 참고 : https://blog.jiniworld.me/51 
        https://cusonar.tistory.com/13?category=607756
        https://programmer93.tistory.com/42
*/

/* 기본적인 웹 인증에 대한 부분을 구현 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages= {"kr.com.inspect"})
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("customAuthenticationSuccessHandler")
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	@Qualifier("customAuthenticationFailureHandler")
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	/* AuthenticationManagerBuilder에 커스터마이징한 MemberService 적용 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService);
	}
	
	/* Spring Security Filter Chain에 접근하여 url 인증 및 인가 처리 시행 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* 권한별 접근 페이지 설정 */
		http.authorizeRequests()    
			.antMatchers("/login", "/login/**").permitAll() //모든 사용자가 사용하는 페이지
			.antMatchers("/", "/**").access("hasRole('ROLE_VIEW')") //ROLE_VIEW 권한을 가진 사용자만 접근
			.anyRequest().authenticated() //설정되지 않은 모든 url request는 인가된 사용자만 이용
	  .and()
	  	/* 로그인 폼과 관련된 설정 */
	    	.formLogin()
	    	.loginPage("/login") //커스텀 로그인 페이지
	    	.successHandler(customAuthenticationSuccessHandler) //로그인 성공시 처리
			.failureHandler(customAuthenticationFailureHandler) //로그인 실패시 처리
	    	.usernameParameter("member_id").passwordParameter("pwd") //아이디, 비밀번호 이름 설정
	  .and()
	    	.logout().permitAll()		
	  .and()
	  		/* csrf 공격 방지를 위해 설정 (이 속성을 설정하면, post 요청시 반드시 _csrf token값 넣기) */
	    	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	/* 특정 url로 접속할 때 인증/인가 처리를 무시 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resource/**"); //정적 파일
	}
}