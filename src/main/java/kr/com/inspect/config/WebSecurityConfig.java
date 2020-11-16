//package kr.com.inspect.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
///* 참고 : https://blog.jiniworld.me/51 */
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
//	/* 프로퍼티에 설정했던 보안 관련 설정을 인메모리에 설정할 수 있도록 함 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//	}
//	
//	/* Spring Security Filter Chain에 접근하여 url 인증 및 인가 처리 시행 */
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	  http.authorizeRequests()    
//	  	/* /, 와 login, register api 관련 url은 모든 사용자가 사용 */
//	    .antMatchers("/", "/login", "/register").permitAll()
//	    /* /main, /** url은 ROLE_VIEW 권한을 가진 사용자만 접근 */
//	    .antMatchers("/main", "/**").access("hasRole('ROLE_VIEW')")
//	    /* 설정되지 않은 모든 url request는 인가된 사용자만 이용할 수 있도록 합니다 */
//	    .anyRequest().authenticated()
//	  .and()
//	  	/* loginPage("/login") 으로 로그인폼페이지 url을 작성. 로그인이 성공했을 시엔 "/main" 로 이동 */
//	    .formLogin().loginPage("/login").defaultSuccessUrl("/main").permitAll()
//	    /* 로그인폼에서 사용자 검증을 할 username, password input name을 설정 */
//	    .usernameParameter("member_id").passwordParameter("pwd")
//	  .and()
//	    .logout().permitAll()		
//	  .and()
//	  	/* csrf 공격 방지를 위해 설정 (이 속성을 설정하면, post 요청시 반드시 _csrf token값 넣기) */
//	    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//	}
//
//	/* 특정 url로 접속할 때 인증/인가 처리를 무시 */
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	  web.ignoring().antMatchers("/resource/**"); //정적 파일
//	}
//}