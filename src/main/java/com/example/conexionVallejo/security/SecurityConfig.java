package com.example.conexionVallejo.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/inicio", "/css/**", "/assets/**", "/js/**").permitAll();
			auth.anyRequest().authenticated();
		})
				
				.formLogin()

				.successHandler(successHandler()). //URL  a donde  se va ir al iniciar sesion
				permitAll()
				.and()
 .sessionManagement()
 .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //ALWAYS - IF_REQUIRED  - NEVER  - STATELESS
.invalidSessionUrl("/login")
.maximumSessions(1)
.expiredUrl("/login")
.sessionRegistry(sessionRegistry())
 .and()
 .sessionFixation()
 .migrateSession()
 .and()
				.build();

	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return 	new SessionRegistryImpl();
	}
	
	
	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {

			response.sendRedirect("/foro");
		});
	}
}
