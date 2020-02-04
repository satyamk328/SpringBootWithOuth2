package com.satyam.security.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.satyam.security.service.auth.CustomAccessDeniedHandler;
import com.satyam.security.service.auth.CustomAuthenticationEntryPoint;
import com.satyam.security.service.auth.CustomLogoutHandler;

/**
 * The server hosting the protected resource, capable of accepting and
 * responding to responding to protected resource request using access tokens.
 *
 * @author Satyam Kumar.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "resource_id";

	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomLogoutHandler customLogouthandler;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false).authenticationEntryPoint(authenticationEntryPoint);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.anonymous().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll();
		http.authorizeRequests().antMatchers("/actuator/**").permitAll();
		http.authorizeRequests().antMatchers("/about").permitAll() ;
		http.authorizeRequests().antMatchers("/signup").permitAll();
		http.authorizeRequests().antMatchers("/oauth/token").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.logout().logoutUrl("/api/v0/logout").logoutSuccessHandler(customLogouthandler);
	}

}