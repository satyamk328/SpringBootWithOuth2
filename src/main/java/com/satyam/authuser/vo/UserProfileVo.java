package com.satyam.authuser.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.satyam.authuser.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileVo implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String phone;
	private String password;
	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
	private Boolean enabled = true;

	private Collection<? extends GrantedAuthority> authorities;

	public UserProfileVo(Long id, String firstName, String lastName, String username, String password, String phone,
			 Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.authorities = authorities;
	}

	public static UserProfileVo build(User user) {
		Set<GrantedAuthority> authorities = Collections.unmodifiableSet(sortAuthorities(user));

		return new UserProfileVo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getPassword(), user.getPhone(), authorities);
	}

	private static Set<GrantedAuthority> sortAuthorities(User user) {
		Set<GrantedAuthority> sortedAuthorities = new HashSet<>();
		if (user.getRole() != null) {
			String name = user.getRole().getRoleName().toUpperCase();
			sortedAuthorities.add(new SimpleGrantedAuthority(name));
		}
		return sortedAuthorities;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserProfileVo user = (UserProfileVo) o;
		return Objects.equals(id, user.id);
	}

}
