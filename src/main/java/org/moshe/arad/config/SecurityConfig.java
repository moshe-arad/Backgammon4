package org.moshe.arad.config;

import org.moshe.arad.security.BackgammonUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BackgammonUserDetailsService backgammonUserDetailsService;
	
	@Override
	protected UserDetailsService userDetailsService() {
		return backgammonUserDetailsService;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", 
				"/app/controllers/**", "/app/partials/**", "/app/*",
				"/", "/index.html", "/favicon.ico", "/webjars/**", "/backgammon-websocket/**",
				"/shutdown");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.httpBasic()
			.and()
			.authorizeRequests().antMatchers("/users/**", "/index.html", "/authenticateUser").permitAll()
			.anyRequest()
			.authenticated();			
	}
}
