package com.satyam.security.service.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationFailureEventListener
		implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
		// write custom code here login failed audit.
		log.info("User Oauth2 login Failed");
		log.info(e.getAuthentication().getPrincipal().toString());
	}

}
