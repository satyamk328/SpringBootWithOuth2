package com.satyam.authuser.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.satyam.authuser.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	Optional<User> findByphone(String phone);
}
