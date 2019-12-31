package edu.course.ws.services;

import edu.course.ws.Utilities.Util;
import edu.course.ws.dto.UserDto;
import edu.course.ws.entities.UserEntity;
import edu.course.ws.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Util util;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setUserId(util.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setEmailVerificationStatus(false);
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto newUserDto = new UserDto();

        BeanUtils.copyProperties(storedUserDetails, newUserDto);

        return newUserDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (null != user) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user.get(), userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public ArrayList<UserDto> getAllUsers() {
        Iterable<UserEntity> users = userRepository.findAll();
        ArrayList<UserDto> usersDto = new ArrayList<>();
        if (null != users) {
            users.forEach(user -> {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                usersDto.add(userDto);
            });
            return usersDto;
        }
        return new ArrayList();
    }

    @Override
    public UserDto updateUser(String userID, UserDto userDto) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userID);
        if(null != userEntity) {
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());

            userEntity = userRepository.save(userEntity);

            BeanUtils.copyProperties(userEntity, returnValue);
        }
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userID) {
        UserEntity userEntity = userRepository.findByUserId(userID);
        UserDto userDto = null;

        if(null != userEntity){
            userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
        }
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        usersPage.forEach(userPage ->{
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userPage, userDto);
            returnValue.add(userDto);
        });

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (null == userEntity) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}

