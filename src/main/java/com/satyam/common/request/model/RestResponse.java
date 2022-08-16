package com.satyam.common.request.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper for rest response containing status and message
 * 
 * @author satyam.kumar
 *
 * @param <T>
 */
@Getter
@Setter
public class RestResponse<T> {

	private T data;
	private RestStatus<?> status;
	private RestCustom custom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();

	public RestResponse() {
	}

	public RestResponse(final T data, final RestStatus<?> status) {
		this.data = data;
		this.status = status;
	}

	public RestResponse(final T data, final RestStatus<?> status, final RestCustom custom) {
		this.data = data;
		this.status = status;
		this.custom = custom;
	}
}
