package com.satyam.authuser.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "date_modified")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateModified;
	
	@Column(name="Channel_type")
	private String channelType;
	
}
