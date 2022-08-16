package com.satyam.authuser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
@Table(name = "user_master")
public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "user_seq", allocationSize = 1)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column
	private String username;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "phone", unique = true)
	private String phone;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "isactive", columnDefinition = "boolean default true", nullable = false)
	private Boolean isActive;
	@Column(name = "isdelete", columnDefinition = "boolean default false", nullable = false)
	private Boolean isDelete;
	@Column(name = "islock", columnDefinition = "boolean default false", nullable = false)
	private Boolean accountNonLocked;
	@Column(name = "account_expired", columnDefinition = "boolean default false", nullable = false)
	private boolean accountNonExpired;
	@Column(name = "credentials_expired", columnDefinition = "boolean default false", nullable = false)
	private boolean credentialsNonExpired;
	@Column(name = "enabled", columnDefinition = "boolean default false", nullable = false)
	private boolean enabled;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false, updatable = true, insertable = true)
	private UserRole role;

}
