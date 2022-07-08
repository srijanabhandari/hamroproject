package com.training.menu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.training.menu.models.CreateUserRequest;
import com.training.menu.models.User;
import com.training.menu.models.UsersResponse;

public interface UserService {

	
public User findUserByEmail(String emailId) throws JsonMappingException, JsonProcessingException;

UsersResponse createUser(CreateUserRequest createUserRequest) throws JsonProcessingException;
	
}
