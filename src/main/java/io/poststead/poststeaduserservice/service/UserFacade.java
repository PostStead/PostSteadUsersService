package io.poststead.poststeaduserservice.service;

import io.poststead.poststeaduserservice.exception.user_exception.UserAlreadyExistsException;
import io.poststead.poststeaduserservice.model.UserEntity;
import io.poststead.poststeaduserservice.model.dto.UserAuthDto;
import io.poststead.poststeaduserservice.model.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@AllArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserDetailsDto getUserByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username);
        return UserDetailsDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    public URI addUser(UserAuthDto user) {
        UserEntity newUserEntity = userService.addUser(user);
        return userService.createUserURI(newUserEntity);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        UserEntity existingUserEntity = userService.getUserByUsername(username);

        if (username.equals(existingUserEntity.getUsername())) {
            throw new UserAlreadyExistsException(username);
        }

        return userService.updateUser(existingUserEntity, userDetailsDto);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

}
