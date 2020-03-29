package edu.course.ws.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRest extends ResourceSupport {

    private String addressId;
    private String country;
    private String city;
    private String streetName;
    private String postalCode;
    private String typeAddress;

}
