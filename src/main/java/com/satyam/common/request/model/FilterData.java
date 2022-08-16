package com.satyam.common.request.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterData<T> {
	
  private List<T> content;
  private Long totalPages;
  private Long totalElements;
  
}



