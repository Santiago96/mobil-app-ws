package edu.course.ws.services;

import edu.course.ws.dto.AddressDTO;
import edu.course.ws.entities.AddressEntity;
import edu.course.ws.entities.UserEntity;
import edu.course.ws.repositories.AddressRepository;
import edu.course.ws.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> findAddressesByUserId(String userId) {
        ModelMapper modelMapper = new ModelMapper();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        List<AddressEntity> addressEntities;

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(null != userEntity) {
            addressEntities = addressRepository.findByUserDTO(userEntity);
            addressEntities.forEach(addressEntity -> {
                addressDTOList.add(modelMapper.map(addressEntity, AddressDTO.class));
            });
        }
        return addressDTOList;
    }

    @Override
    public AddressDTO findAddressByUserIdAndAddressId(String userId, String addressId) {

        AddressDTO addressDTO = new AddressDTO();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(null != userEntity) {
            AddressEntity addressEntity = addressRepository.findByUserDTOAndAddressId(userEntity, addressId);
            addressDTO = new ModelMapper().map(addressEntity, AddressDTO.class);
        }

        return addressDTO;
    }
}
