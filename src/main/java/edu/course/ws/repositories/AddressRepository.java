package edu.course.ws.repositories;

import edu.course.ws.entities.AddressEntity;
import edu.course.ws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    List<AddressEntity> findByUserDTO(UserEntity userEntity);

    AddressEntity findByUserDTOAndAddressId(UserEntity userEntity, String addressId);
}
