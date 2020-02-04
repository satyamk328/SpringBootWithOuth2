package com.satyam.security.service.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.satyam.authuser.model.User;

/***
 * 
 * @author SKumar
 *
 */
public class CustomTokenEnhancer extends JwtAccessTokenConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(getAdditionalInfo(authentication));
		return accessToken;
	}

	private Map<String, Object> getAdditionalInfo(OAuth2Authentication oAuth2Authentication) {
		User loginUser = (User) oAuth2Authentication.getPrincipal();

		final Map<String, Object> additionalInfo = new HashMap<>();
		additionalInfo.put("authorities", loginUser.getAuthorities());
		additionalInfo.put("username", loginUser.getUsername());
		additionalInfo.put("isEnabled", loginUser.isEnabled());
		additionalInfo.put("account_Expired", loginUser.isAccountNonExpired());
		additionalInfo.put("account_locked", loginUser.isAccountNonLocked());
		additionalInfo.put("credentials_expired", loginUser.isCredentialsNonExpired());
		return additionalInfo;
	}
}
