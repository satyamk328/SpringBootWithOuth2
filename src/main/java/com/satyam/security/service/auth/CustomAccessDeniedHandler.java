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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.texala.common.request.model.RestCustom;
import net.texala.common.request.model.RestResponse;
import net.texala.common.request.model.RestStatus;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	
	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException {
		// This is invoked when user tries to access a secured REST resource without
		// supplying any credentials
		// We should just send a 401 Unauthorized response because there is no 'login
		// page' to redirect to
		log.error("Responding with FORBIDDEN error. Message - {}", e.getMessage());
		
		RestCustom custom = RestCustom.builder().build();
		custom.setMessage(e);
		RestStatus<String> status = new RestStatus<>(HttpStatus.FORBIDDEN, "Access Denied");
		RestResponse<?> restResponse = new RestResponse<>(null, status, custom);

		ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
		outputMessage.setStatusCode(HttpStatus.FORBIDDEN);
		HttpMessageConverter<String> messageConverter = new StringHttpMessageConverter();
		messageConverter.write(new ObjectMapper().writeValueAsString(restResponse), MediaType.APPLICATION_JSON,
				outputMessage);
	}
}