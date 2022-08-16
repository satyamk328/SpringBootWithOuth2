package com.satyam.security.service.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyam.authuser.vo.UserProfileVo;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
		OAuth2Authentication authentication = super.extractAuthentication(map);
		Authentication userAuthentication = authentication.getUserAuthentication();
		if (userAuthentication != null) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<Object, Object> userDetails = (LinkedHashMap<Object, Object>) map.get("userDetails");
			if (userDetails != null) {
				UserProfileVo extendedPrincipal = new ObjectMapper().convertValue(map.get("userDetails"), UserProfileVo.class);
				//extendedPrincipal.setAuthorities((Collection<? extends GrantedAuthority>)map.get("authorities"));
				Collection<? extends GrantedAuthority> authorities = userAuthentication.getAuthorities();
				userAuthentication = new UsernamePasswordAuthenticationToken(extendedPrincipal,
						userAuthentication.getCredentials(), authorities);
			}
		}
		return new OAuth2Authentication(authentication.getOAuth2Request(), userAuthentication);
	}
}