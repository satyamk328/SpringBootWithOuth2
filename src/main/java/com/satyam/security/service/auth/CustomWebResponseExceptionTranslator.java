package com.satyam.security.service.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;

import com.satyam.common.request.model.RestCustom;
import com.satyam.common.request.model.RestResponse;
import com.satyam.common.request.model.RestStatus;

@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	public ResponseEntity<RestResponse<?>> translate(Exception e) throws Exception {
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
		RuntimeException ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class,
				causeChain);

		if (ase != null) {
			return handleOAuth2Exception((OAuth2Exception) ase);
		}

		ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
				causeChain);
		if (ase != null) {
			return handleOAuth2Exception(new OAuth2Exception(e.getMessage(), e));
		}

		ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class,
				causeChain);
		if (ase instanceof AccessDeniedException) {
			return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
		}

		ase = (UnauthorizedClientException) throwableAnalyzer.getFirstThrowableOfType(UnauthorizedClientException.class,
				causeChain);
		if (ase instanceof UnauthorizedClientException) {
			return handleOAuth2Exception(new UnAuthorizedException(ase.getMessage(), ase));
		}
		
		return handleOAuth2Exception(new ServerErrorException(e.getMessage(), e));

	}

	private ResponseEntity<RestResponse<?>> handleOAuth2Exception(OAuth2Exception e) throws IOException {

		int status = e.getHttpErrorCode();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
			headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
		}

		RestCustom custom = RestCustom.builder().build();
		custom.setMessage(e);
		RestResponse<String> response = new RestResponse<>(null, new RestStatus<>(HttpStatus.valueOf(e.getHttpErrorCode()), e.getMessage()), custom);
		return new ResponseEntity<>(response, HttpStatus.valueOf(status));
	}

	public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
		this.throwableAnalyzer = throwableAnalyzer;
	}

	private static class ForbiddenException extends OAuth2Exception {

		public ForbiddenException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "access_denied";
		}

		public int getHttpErrorCode() {
			return 403;
		}

	}

	private static class ServerErrorException extends OAuth2Exception {

		public ServerErrorException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "server_error";
		}

		public int getHttpErrorCode() {
			return 500;
		}

	}

	private static class UnAuthorizedException extends OAuth2Exception {

		public UnAuthorizedException(String msg, Throwable t) {
			super(msg, t);
		}

		public String getOAuth2ErrorCode() {
			return "User is not authorised to use the Admin Application";
		}

		public int getHttpErrorCode() {
			return 401;
		}

	}

}
