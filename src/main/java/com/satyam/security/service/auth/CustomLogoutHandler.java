package com.satyam.security.service.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyam.common.request.model.RestResponse;
import com.satyam.common.request.model.RestStatus;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutSuccessHandler {

	private static final String BEARER_AUTHENTICATION = "Bearer ";
	private static final String HEADER_AUTHORIZATION = "Authorization";

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException {

		Assert.notNull(tokenStore, "tokenStore must be set");
		String token = request.getHeader(HEADER_AUTHORIZATION);
		Assert.hasText(token, "token must be set");
		if (token != null && (token.startsWith(BEARER_AUTHENTICATION) || token.startsWith("bearer"))) {
			// Condition true and spilt to access the token and remove from the token-store
			OAuth2AccessToken existingAccessToken = tokenStore.readAccessToken(token.split(" ")[1]);
			OAuth2RefreshToken refreshToken;
			if (existingAccessToken != null) {
				if (existingAccessToken.getRefreshToken() != null) {
					log.info("remove refreshToken!", existingAccessToken.getRefreshToken());
					refreshToken = existingAccessToken.getRefreshToken();
					tokenStore.removeRefreshToken(refreshToken);
				}
				log.info("remove existingAccessToken!", existingAccessToken);
				tokenStore.removeAccessToken(existingAccessToken);
			}
			return;
		}
		final PrintWriter writer = response.getWriter();
		final RestResponse<String> responseObj = new RestResponse<>(null,
				new RestStatus<>(HttpStatus.OK, "User Successfully Logout"));
		final String result = new ObjectMapper().writeValueAsString(responseObj);
		log.info(result);
		writer.write(result);
		writer.flush();
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
