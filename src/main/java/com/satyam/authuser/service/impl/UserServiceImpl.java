package com.satyam.authuser.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.satyam.authuser.dao.UserDao;
import com.satyam.authuser.model.User;
import com.satyam.authuser.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		User user = null;
		if (username.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
			user = this.userDao.findByphone(username)
					.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		} else {
			user = this.userDao.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
		}
		return user;
	}


	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userDao.deleteById(id);
	}

	@Override
	public User save(User user) {
		return userDao.save(user);
	}
}
