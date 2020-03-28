package edu.course.ws.services;

import edu.course.ws.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    List<AddressDTO> findAddressesByUserId(String userId);

    AddressDTO findAddressByUserIdAndAddressId(String userId, String addressId);
}
