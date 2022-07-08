package com.training.menu.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
	String name;
	String salary;
	String age;
	

}
