package com.springBootBoard_PersonalEdition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*특정 URL에 대해 인증 없이 접근 권한을 부여함*/
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**"))
//        	.permitAll()
//        	.and()
//            .csrf().ignoringRequestMatchers(
//                    new AntPathRequestMatcher("/h2-console/**"))
//            .and()
//            .headers()
//            .addHeaderWriter(new XFrameOptionsHeaderWriter(
//                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
		
		http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**"))
			.permitAll()
			.and()
			.csrf(csrf -> csrf.ignoringRequestMatchers(
        		new AntPathRequestMatcher("/h2-console/**")))
			.headers(headers -> headers.addHeaderWriter(
				new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));
		return http.build();
	}
}
