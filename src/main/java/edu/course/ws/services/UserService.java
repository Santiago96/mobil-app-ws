package edu.course.ws.services;

import edu.course.ws.dto.UserDto;
import edu.course.ws.model.rest.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long id);

    ArrayList<UserDto> getAllUsers();

    UserDto updateUser(String userID, UserDto userDto);

    UserDto getUserByUserId(String userID);

    List<UserDto> getAllUsers(int page, int limit);



}
