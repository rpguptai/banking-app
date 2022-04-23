/**
 * 
 */
package com.bank.account.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration for local
 *
 */

@Configuration
@EnableWebSecurity
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
					.antMatchers("/api/**")
					.authenticated()
					.and().httpBasic()
					.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
					.and().csrf().disable();
		
		http.headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("admin")
		.password("$2a$10$reSdSf/1mU.tV8KCgInMcuwQLAEHumWehYyq049ScPsCENwP3NG2.")
				.roles("USER");
	}

}
