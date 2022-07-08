package com.training.menu.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.menu.models.CreateUserRequest;
import com.training.menu.models.CreateUserResponse;
import com.training.menu.models.User;
import com.training.menu.models.UserException;
import com.training.menu.models.UserResponse;
import com.training.menu.models.UsersResponse;
import com.training.menu.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private RestTemplate restTemplate;
	
	private ObjectMapper objectMapper;	

	public UserServiceImpl(@Autowired RestTemplate restTemplate, @Autowired ObjectMapper objectMapper) {
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public User findUserByEmail(String emailId) throws UserException, JsonMappingException, JsonProcessingException {
		if(emailId.equals(""))
		{
			throw new UserException("Email ID cannot be empty", HttpStatus.BAD_REQUEST);
		}
		
		String response = restTemplate.getForObject("https://reqres.in/api/users?page=1", String.class);
		UserResponse userResponse = objectMapper.readValue(response, UserResponse.class);
		for(int i = 1; i<= userResponse.getTotal_pages(); i++) {
			String url = "https://reqres.in/api/users?page="+i;
			
			response= restTemplate.getForObject(url,String.class );
			UserResponse userResponse2 = objectMapper.readValue(response, UserResponse.class);
					for(User u: userResponse2.getData())
					{
						if(u.getEmail().equals(emailId))
							return u;
		}
		}
		
		
		
			throw new UserException("User cannot be found", HttpStatus.BAD_REQUEST);
		
	}

	@Override
	public UsersResponse createUser(CreateUserRequest createUserRequest) throws JsonProcessingException {
		if(createUserRequest.getName()==null||createUserRequest.getName().equals(""))
		{
			throw new UserException("Name cannot be empty", HttpStatus.BAD_REQUEST);
		}
		if(createUserRequest.getSalary()==null||createUserRequest.getSalary().equals(""))
		{
			throw new UserException("Salary cannot be empty", HttpStatus.BAD_REQUEST);
		}
		if(createUserRequest.getAge()==null||createUserRequest.getAge().equals(""))
		{
			throw new UserException("Age cannot be empty", HttpStatus.BAD_REQUEST);
		}
		HttpHeaders headers = new HttpHeaders();
		
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(createUserRequest), headers);
		
		
		ResponseEntity<CreateUserResponse> response = restTemplate.exchange("http://dummy.restapiexample.com/api/v1/create", HttpMethod.POST, entity,CreateUserResponse.class);
		
		return response.getBody().getData();
	}
	
	

}
