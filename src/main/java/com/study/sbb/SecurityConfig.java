package com.study.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //이 파일이 스프링의 환경 설정 파일임을 의미
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티 제어를 받게 만듬. 스프링 시큐리티를 활성화하는 역할
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 사용 시 반드시 필요한 설정
public class SecurityConfig {
	//내부적으로 SecurityFilterChain 클래스가 동작해 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정 가능
	@Bean //스프링 시큐리티 세부 설정 @Bean 통해 SecurityFilterChain 빈을 생성하여 설정 가능
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
						.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
				// /h2-console/로 시작하는 모든 URL은 CSRF 검증을 하지 않는다는 설정을 추가
				.csrf((csrf) -> csrf
						.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
				//X-Frame-Options 헤더를 DENY 대신 SAMEORIGIN으로 설정해 오류 발생 방지
				//SAMEORIGIN 설정 시 프레임에 포함된 웹 페이지가 동일한 사이트에서 제공할 때만 사용 허락
				.headers((headers) -> headers
						.addHeaderWriter(new XFrameOptionsHeaderWriter(
								XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
				//스프링 시큐리티 로그인 설정 담당하는 부분
				.formLogin((formLogin) -> formLogin
					.loginPage("/user/login") //로그인 페이지 URL
					.defaultSuccessUrl("/")) //로그인 성공 시 이동할 페이지 루트 URL
				//로그아웃 기능 구현 위한 설정 추가
				.logout((logout) -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) //로그아웃 시 생성된 사용자 세션도 삭제하도록 처리
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true))
		;
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		//스프링 시큐리티 인증 처리
		return authenticationConfiguration.getAuthenticationManager();
	}
}
