package com.satyam.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.satyam.authuser.model.User;
import com.satyam.authuser.service.UserService;
import com.satyam.common.request.model.RestCustom;
import com.satyam.common.request.model.RestResponse;
import com.satyam.common.request.model.RestStatus;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<RestResponse<?>> getCurrentUser1(OAuth2Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
    	 String auth = (String) authentication.getUserAuthentication().getPrincipal();
         String role = authentication.getAuthorities().iterator().next().getAuthority();
        log.error(auth+" :::::::::::::::::::::: "+role);
        
        RestStatus<?> restStatus = new RestStatus<String>(HttpStatus.OK, "Request fetch successfully");
        RestResponse<?> response = new RestResponse(authentication, restStatus, RestCustom.builder().build());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public List<User> listUser(OAuth2Authentication authentication){
        return userService.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User create(@RequestBody User user){
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id){
        userService.delete(id);
        return "success";
    }
   
}
