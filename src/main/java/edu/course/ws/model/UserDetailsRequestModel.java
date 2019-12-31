package edu.course.ws.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDetailsRequestModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
