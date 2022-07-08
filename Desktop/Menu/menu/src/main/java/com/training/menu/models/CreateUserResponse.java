package com.training.menu.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
	String status;
	UsersResponse data;
	

}

