package com.soutenances.soutenances.Security;

import com.soutenances.soutenances.Filter.JwtAuthenticationFilter;
import com.soutenances.soutenances.Service.UserDetailServiceI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final UserDetailServiceI UserDetI;
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;


	public SecurityConfig(UserDetailServiceI UserDetI,JwtAuthenticationFilter jwtfl)
	{
		this.UserDetI=UserDetI;
		this.jwtAuthenticationFilter=jwtfl;

	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		return  http
         .csrf(AbstractHttpConfigurer::disable)
         .authorizeHttpRequests(
                 req->req.requestMatchers("/api/auth/**")
                         .permitAll()
                         .requestMatchers("/api/adminController/**").hasAuthority("Admin")
                         .requestMatchers("/api/SalleController**").hasAuthority("Admin")
						 .requestMatchers("/api/api/SpecialiteController**").hasAuthority("Admin")
						 .requestMatchers("/api/api/GroupeController**").hasAuthority("Admin")
						 .requestMatchers("/api/api/UserController**").hasAuthority("Admin")
						 .requestMatchers("/ap/projetController**").hasAuthority("Admin")


                         .anyRequest()
                         .authenticated())
      
         .userDetailsService(UserDetI)
         .sessionManagement(session->session
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
         .exceptionHandling(
                 e->e.accessDeniedHandler(
                                 (request, response, accessDeniedException)->response.setStatus(403)
                         )
                         .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

         .build();

         

		}
	  @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

}