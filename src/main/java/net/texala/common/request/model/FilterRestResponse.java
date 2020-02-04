package net.texala.common.request.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterRestResponse<T> {

	private FilterData<T> data;
	private RestStatus<?> status;
	private RestCustom custom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();

	public FilterRestResponse() {
	}

	public FilterRestResponse(final FilterData<T> data, final RestStatus<?> status) {
		this.data = data;
		this.status = status;
	}

	public FilterRestResponse(final FilterData<T> data) {
		this.data = data;
	}

	public FilterRestResponse(final FilterData<T> data, final RestStatus<?> status, final RestCustom custom) {
		this.data = data;
		this.status = status;
		this.custom = custom;
	}
}

