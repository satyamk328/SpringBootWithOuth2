package com.satyam.security.service.auth;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.satyam.authuser.vo.UserProfileVo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	public void onApplicationEvent(AuthenticationSuccessEvent e) {

		Object principal = e.getAuthentication().getPrincipal();
		if (principal instanceof UserProfileVo) {
			// write custom code here for login success audit
			UserProfileVo user = (UserProfileVo) principal;
			log.info("User Oauth2 login success");
			log.info("This is success event : " + user);
		}

	}
}
