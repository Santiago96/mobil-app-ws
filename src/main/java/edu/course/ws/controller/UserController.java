package edu.course.ws.controller;

import edu.course.ws.dto.AddressDTO;
import edu.course.ws.dto.UserDto;
import edu.course.ws.model.UserDetailsRequestModel;
import edu.course.ws.model.rest.AddressRest;
import edu.course.ws.model.rest.UserRest;
import edu.course.ws.services.AddressService;
import edu.course.ws.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping("/all")
    public ArrayList<UserRest> getAllUsers() {
        ArrayList<UserDto> usersDto = userService.getAllUsers();
        ArrayList<UserRest> usersRest = new ArrayList<>();


        usersDto.forEach(userDto -> {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            usersRest.add(userRest);
        });
        return usersRest;

    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUserById(@PathVariable Long id) {
        UserDto userDTO = userService.getUserById(id);

        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(userDTO, returnValue);

        return returnValue;
    }

    @GetMapping(value = "userId/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUserByUserId(@PathVariable String userId) {
        UserDto userDTO = userService.getUserByUserId(userId);

        UserRest returnValue = new ModelMapper().map(userDTO, UserRest.class);


        return returnValue;
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserDto user = userService.getUserByEmail(userDetails.getEmail());
        if (null == user) {
            ModelMapper modelMapper = new ModelMapper();
            UserDto userDto = modelMapper.map(userDetails, UserDto.class);

            UserDto createdUser = userService.createUser(userDto);
            UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
            return returnValue;
        }
        throw new RuntimeException("Record already exists");
    }

    @GetMapping(path = "/email/{email}")
    public UserRest getUserByEmail(@PathVariable String email) {
        UserDto user = userService.getUserByEmail(email);
        if (null != user) {
            UserRest returnValue = new UserRest();
            BeanUtils.copyProperties(user, returnValue);
            return returnValue;
        }
        return null;
    }

    @PutMapping(path = "/{userID}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest updateUser(@PathVariable String userID, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto userUpdated = userService.updateUser(userID, userDto);
        BeanUtils.copyProperties(userUpdated, returnValue);
        return returnValue;

    }

    @DeleteMapping
    public String deleteUser() {
        return "DELETE was called";
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public List<UserRest> getUsersPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> users = new ArrayList<>();

        List<UserDto> usersDto = userService.getAllUsers(page, limit);

        usersDto.forEach(userDto -> {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            users.add(userRest);
        });


        return users;
    }

    @GetMapping(path = "{userId}/addresses",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AddressRest> getAddressesByUserId(@PathVariable String userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<AddressDTO> addressDTOList = addressService.findAddressesByUserId(userId);
        List<AddressRest> addressRestList = new ArrayList<>();

        addressDTOList.forEach(addressDTO -> {
            addressRestList.add(modelMapper.map(addressDTO, AddressRest.class));
        });

        return addressRestList;
    }

    @GetMapping(path = "{userId}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressRest getAddressByAddressId(@PathVariable String userId, @PathVariable String addressId) {
        AddressRest addressRest;

        AddressDTO addressDTO = addressService.findAddressByUserIdAndAddressId(userId, addressId);

        Link addressLink = linkTo(methodOn(UserController.class).getAddressByAddressId(userId, addressId)).withSelfRel();
        Link userLink = linkTo(methodOn(UserController.class).getUserByUserId(userId)).withRel("user");

        addressRest = new ModelMapper().map(addressDTO, AddressRest.class);
        addressRest.add(addressLink);
        addressRest.add(userLink);

        return addressRest;
    }
}
