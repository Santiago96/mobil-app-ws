package edu.course.ws.model;

import lombok.*;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
