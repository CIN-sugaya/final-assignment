package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/admin/**").authenticated()
	            .anyRequest().permitAll()
	        )
	        .formLogin(form -> form
	            .loginPage("/admin/login") // カスタムログインページのパス
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .permitAll()
	        );

	    return http.build();
	}
}
