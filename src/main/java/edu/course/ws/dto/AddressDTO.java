package edu.course.ws.dto;

import lombok.*;

import java.io.Serializable;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable {

    private long id;
    private String addressId;
    private String country;
    private String city;
    private String streetName;
    private String postalCode;
    private String typeAddress;
    private UserDto userDto;
}
