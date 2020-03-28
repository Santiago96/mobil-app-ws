package edu.course.ws.model;

import lombok.*;

import java.util.List;

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
	private List<AddressRequestModel> addresses;

}
