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
import com.satyam.authuser.vo.UserProfileVo;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userDao.findByEmailIgnoreCase(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User %s was not found in system", username)));
		return UserProfileVo.build(user);
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
