package com.satyam.security.service.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.satyam.security.service.auth.CustomAccessDeniedHandler;
import com.satyam.security.service.auth.CustomAuthenticationEntryPoint;
import com.satyam.security.service.auth.CustomLogoutHandler;

/**
 * 
 * @author SKumar
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource(name = "userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomLogoutHandler customLogouthandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(encoder());
		return authenticationProvider;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/**");
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-ui.html", "/resources/**", "/webjars/**",
				"/swagger-resources/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.anonymous();
		http.headers().frameOptions().disable();
		http.httpBasic().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Allow access to swagger
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/v0/user/**").permitAll();
		http.authorizeRequests()
				.antMatchers("/v0/user/findUser/**", "/v0/user/resendverificationemail/**", "/v0/user/resend/email/**",
						"/v0/user/verify/**", "/v0/user/changepassword/**", "/v0/logout", "/**/downloadFile/**",
						"/**/bussinessType/", "/**/associateServiceType/", "/**/bussinessStructure/",
						"/**/expectedAnnualVolume/", "/**/licenseType/", "/**/designation/all", "/**/user/validate", 
						"/**/insuranceType/", "/**/upload/", "/**/updateAgreementStatus/**", "/v0/user/find/**",
						"/v0/referralUsers/findUserByCreatedBy")
				.permitAll();
		http.authorizeRequests().antMatchers("/oauth/token", "/oauth/authorize", "/oauth/check_token").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
		http.logout().logoutUrl("/v0/logout").logoutSuccessHandler(customLogouthandler);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
