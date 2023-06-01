package com.github.caioorleans.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;

import com.github.caioorleans.security.JWT.JwtConfigurer;
import com.github.caioorleans.security.JWT.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap();
		Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", encoder);
		DelegatingPasswordEncoder passwordEncoder =  
				new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
		return passwordEncoder;
	}
	
	@Bean
	AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
			.httpBasic().disable()
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests( authorizeHttpRequests -> authorizeHttpRequests
							.requestMatchers(
									"/auth/signin", 
									"/auth/refresh/**", 
									"/auth/**",
									"/swagger-ui/**",
									"v3/api-docs/**").permitAll()
							.requestMatchers("/api/**").authenticated()
							.requestMatchers("/users").denyAll()
					)
			.cors()
			.and()
			.apply(new JwtConfigurer(tokenProvider))
			.and()
			.build();
	}
}