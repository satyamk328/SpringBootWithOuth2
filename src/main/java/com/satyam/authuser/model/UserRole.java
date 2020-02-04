package com.satyam.authuser.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "role_master")
public class UserRole extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", nullable = false, unique = true)
	private Long roleId;
	@Column(name = "role_name")
	private String roleName;
	@Column(name = "description")
	private String description;
	@Column(name = "is_active", columnDefinition = "boolean default true", nullable = false)
	private Boolean isActive;
	@Column(name = "is_delete", columnDefinition = "boolean default true", nullable = false)
	private Boolean isDelete;
	@Column(name = "is_admin", columnDefinition = "boolean default false", nullable = false)
	private Boolean isAdmin;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<User> users;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
	private RolePrivilege privileges;

}