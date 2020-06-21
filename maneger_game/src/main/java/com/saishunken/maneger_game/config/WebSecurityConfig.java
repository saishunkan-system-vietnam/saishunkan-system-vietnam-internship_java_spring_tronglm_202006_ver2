package com.saishunken.maneger_game.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.saishunken.maneger_game.service.AccountServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
	 @Autowired
     LogoutSuccessHandler logoutSuccessHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Autowired
	private AccountServiceImpl userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/login", "/register").permitAll()
			.antMatchers("/admin/**").hasAuthority("admin")
			.anyRequest().authenticated();

		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
		
		http.formLogin().loginProcessingUrl("/login").successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler).usernameParameter("name_account")
				.passwordParameter("password");

		http.logout()
				.logoutSuccessHandler(logoutSuccessHandler)
				.logoutUrl("/logout")
				.permitAll();
	}
}
