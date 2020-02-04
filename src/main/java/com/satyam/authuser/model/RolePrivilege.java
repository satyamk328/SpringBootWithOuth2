package com.satyam.authuser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "privilege_master")
public class RolePrivilege extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "privilege_id", nullable = false, unique = true)
	private Long privilegeId;
	@Column(name = "privilege_name")
	private String privilegeName;
	@Column(name = "is_add", columnDefinition = "boolean default false", nullable = false)
	private Boolean isAdd;
	@Column(name = "is_edit", columnDefinition = "boolean default false", nullable = false)
	private Boolean isEdit;
	@Column(name = "is_delete", columnDefinition = "boolean default false", nullable = false)
	private Boolean isDelete;
	@Column(name = "is_view", columnDefinition = "boolean default false", nullable = false)
	private Boolean isView;
	@Column(name = "is_import", columnDefinition = "boolean default false", nullable = false)
	private Boolean isImport;
	@Column(name = "is_export", columnDefinition = "boolean default false", nullable = false)
	private Boolean isExport;
	@Column(name = "is_print", columnDefinition = "boolean default false", nullable = false)
	private Boolean isPrint;
	@Column(name = "is_email", columnDefinition = "boolean default false", nullable = false)
	private Boolean isEmail;

	@OneToOne
	private UserRole role;

}
