package kr.com.inspect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 스프링시큐리티 사용을 위한 어노테이션선언
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	// WebSecurityConfigurerAdapter 상속
	private AuthenticationProvider authenticationProvider;

	public WebSecurityConfig(AuthenticationProvider authenticationProvider) {
		// UserDetailsService userDetailsService,
		// PasswordEncoder passwordEncoder
		this.authenticationProvider = authenticationProvider;
	}

	/* * 스프링 시큐리티가 사용자를 인증하는 방법이 담긴 객체. */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// AuthenticationProvider 구현체
		auth.authenticationProvider(authenticationProvider);
		// auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	// 스프링 시큐리티 룰을 무시하게 하는 Url 규칙(여기 등록하면 규칙 적용하지 않음)

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**").antMatchers("/css/**").antMatchers("/vendor/**")
				.antMatchers("/js/**").antMatchers("/favicon*/**").antMatchers("/img/**");
	}
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
						CustomLogoutSuccessHandler logoutSuccessHandler = new CustomLogoutSuccessHandler();
						logoutSuccessHandler.setDefaultTargetUrl("/loginPage?logout=logout");
						return logoutSuccessHandler;
	}
	
	// 스프링 시큐리티 규칙
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()// 보호된 리소스 URI에 접근할 수 있는 권한을 설정
				.antMatchers("/login*/**").permitAll()// 전체 접근 허용
				.antMatchers("/logout/**").permitAll().antMatchers("/myPage").hasRole("ADMIN")// admin이라는 롤을 가진 사용자만 // 접근허용
				.antMatchers("/chatbot/**").permitAll().anyRequest().authenticated().and().logout().logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandler()).and().csrf() // csrf 보안 설정을 비활성화
				.disable() // 해당 기능을 사용하기 위해서는 프론트단에서 csrf토큰값 보내줘야함
				.addFilter(jwtAuthenticationFilter()) // Form Login에 사용되는 custom AuthenticationFilter 구현체를 등록
				.addFilter(jwtAuthorizationFilter()) // Header 인증에 사용되는 BasicAuthenticationFilter 구현체를 등록
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
				.authenticationEntryPoint(authenticationEntryPoint());
	}

	/* * SuccessHandler bean register */
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
						successHandler.setDefaultTargetUrl("/index");
						return successHandler;
	}

	// FailureHandler bean register
	@Bean 
	public AuthenticationFailureHandler authenticationFailureHandler() {
						CustomAuthenticationFailureHandler failureHandler = new CustomAuthenticationFailureHandler();
						failureHandler.setDefaultFailureUrl("/loginPage?error=error");
						return failureHandler; }
					/* * LogoutSuccessHandler bean register */
	
					/* * AccessDeniedHandler bean register */
					@Bean
					public AccessDeniedHandler accessDeniedHandler() {
						CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
						accessDeniedHandler.setErrorPage("/error/403");
						return accessDeniedHandler; 
						}
					/* * AuthenticationEntryPoint bean register */
					@Bean
					public AuthenticationEntryPoint authenticationEntryPoint() {
						return new CustomAuthenticationEntryPoint("/loginPage?error=e");
						}
					/* * Form Login시 걸리는 Filter bean register */
					@Bean public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
						JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
						jwtAuthenticationFilter.setFilterProcessesUrl("/login");
						jwtAuthenticationFilter.setUsernameParameter("username");
						jwtAuthenticationFilter.setPasswordParameter("password");
						jwtAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
						jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
						jwtAuthenticationFilter.afterPropertiesSet(); return jwtAuthenticationFilter;
						}
					/* * Filter bean register */ 
		@Bean
		public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
				JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(authenticationManager());
				return jwtAuthorizationFilter; }
		@Bean
		public PasswordEncoder passwordEncoder() {//간단하게 비밀번호 암호화
				return new BCryptPasswordEncoder(); } }
}
