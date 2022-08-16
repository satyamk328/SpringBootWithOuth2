package com.satyam.security.service.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satyam.common.request.model.RestCustom;
import com.satyam.common.request.model.RestResponse;
import com.satyam.common.request.model.RestStatus;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException {
		// This is invoked when user tries to access a secured REST resource without
		// supplying any credentials
		// We should just send a 401 Unauthorized response because there is no 'login
		// page' to redirect to
		log.error("Responding with unauthorized error. Message - {}", e.getMessage());

		RestCustom custom = RestCustom.builder().build();
		custom.setMessage(e);
		RestStatus<String> status = new RestStatus<>(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
		RestResponse<?> restResponse = new RestResponse<>(null, status, custom);

		ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
		outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
		HttpMessageConverter<String> messageConverter = new StringHttpMessageConverter();
		messageConverter.write(new ObjectMapper().writeValueAsString(restResponse), MediaType.APPLICATION_JSON,
				outputMessage);
	}
}
