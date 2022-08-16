package com.satyam.common.request.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Wrapped in Rest Entity, contains case of exception to be send as response
 * 
 * @author satyam.kumar
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestCustom {

	private String cause;

	public void setMessage(Exception e) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream ps = new PrintStream(baos);
		if (e != null) {
			e.printStackTrace(ps);
		}
		ps.close();
		cause = baos.toString();
	}
	
}