package io.poststead.poststeaduserservice.service;

import io.poststead.poststeaduserservice.exception.user_exception.UserAlreadyExistsException;
import io.poststead.poststeaduserservice.exception.user_exception.UserNotFoundException;
import io.poststead.poststeaduserservice.model.Role;
import io.poststead.poststeaduserservice.model.UserEntity;
import io.poststead.poststeaduserservice.model.dto.UserAuthDto;
import io.poststead.poststeaduserservice.model.dto.UserDetailsDto;
import io.poststead.poststeaduserservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;

import static io.poststead.poststeaduserservice.utility.UserConstants.GET_USER_ROUTE;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity addUser(UserAuthDto userAuthDto) {
        if (userRepository.existsByUsername(userAuthDto.getUserName())) {
            throw new UserAlreadyExistsException(userAuthDto.getUserName());
        }

        return userRepository.save(UserEntity.builder()
            .username(userAuthDto.getUserName())
            .password(passwordEncoder.encode(userAuthDto.getPassword()))
            .role(Role.MEMBER)
            .build());
    }

    public URI createUserURI(UserEntity userEntity) {
        return URI.create(GET_USER_ROUTE + userEntity.getUsername());
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserDetailsDto updateUser(UserEntity userEntity, UserDetailsDto userDetailsDto) {
        UserEntity updatedUserEntity = updateUser(updateUserWithGivenDetails(userEntity, userDetailsDto));
        return UserDetailsDto.builder()
            .id(updatedUserEntity.getId())
            .name(updatedUserEntity.getUsername())
            .firstName(updatedUserEntity.getFirstName())
            .lastName(updatedUserEntity.getLastName())
            .email(updatedUserEntity.getEmail())
            .phone(updatedUserEntity.getPhone())
            .build();
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(String username) {
        userRepository.findByUsername(username)
            .ifPresentOrElse(
                existingUserEntity -> userRepository.deleteByUsername(existingUserEntity.getUsername()),
                () -> {
                    throw new UserNotFoundException(username);
                }
            );
    }

    private UserEntity updateUserWithGivenDetails(UserEntity userEntity, UserDetailsDto userDetailsDto) {
        userEntity.setUsername(userDetailsDto.getName() == null ? userEntity.getUsername() : userDetailsDto.getName());
        userEntity.setFirstName(userDetailsDto.getFirstName() == null ? userEntity.getFirstName() : userDetailsDto.getFirstName());
        userEntity.setLastName(userDetailsDto.getLastName() == null ? userEntity.getLastName() : userDetailsDto.getLastName());
        userEntity.setEmail(userDetailsDto.getEmail() == null ? userEntity.getEmail() : userDetailsDto.getEmail());
        userEntity.setPhone(userDetailsDto.getPhone() == null ? userEntity.getPhone() : userDetailsDto.getPhone());
        return userEntity;
    }

}
