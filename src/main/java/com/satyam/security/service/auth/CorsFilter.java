package com.satyam.security.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * handling Cross Origin Resource Sharing (CORS), making cross-origin AJAX
 * possible.
 * 
 * @author satyam.kumar
 *
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

	public static final String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String originHeader = request.getHeader("Origin");
		// CORS "pre-flight" request
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE,OPTIONS");
		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, userContext, Access-Control-Allow-Origin, Key, Authorization, Access-Control-Request-Headers");
		response.addHeader("Access-Control-Max-Age", "1800");// 30 min

		if (crossOriginRequestFiler(request, response)) {
			return;
		}

		filterChain.doFilter(request, response);
	}

	private boolean crossOriginRequestFiler(HttpServletRequest request, HttpServletResponse response) {
		String origin = request.getHeader("Origin");
		if (origin != null && request.getMethod() != null && "OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setHeader("Access-Control-Allow-Methods", VALID_METHODS);
			response.setHeader("Access-Control-Allow-Origin", origin);
			response.setStatus(200);
			return true;
		}
		return false;
	}

}
