package com.training.menu.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersResponse {
	String name;
	String salary;
	String age;
	int id;

}
