package edu.course.ws.model.rest;

import edu.course.ws.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRest {

    private String addressId;
    private String country;
    private String city;
    private String streetName;
    private String postalCode;
    private String typeAddress;

}
