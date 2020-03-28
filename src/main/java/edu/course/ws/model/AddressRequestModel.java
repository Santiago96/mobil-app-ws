package edu.course.ws.model;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestModel {

    private String country;
    private String city;
    private String streetName;
    private String postalCode;
    private String typeAddress;
}
