package io.poststead.poststeaduserservice.service;

import io.poststead.poststeaduserservice.exception.user_exception.UserAlreadyExistsException;
import io.poststead.poststeaduserservice.model.User;
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
        User user = userService.getUserByUsername(username);
        return UserDetailsDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public URI addUser(UserAuthDto user) {
        User newUser = userService.addUser(user);
        return userService.createUserURI(newUser);
    }

    public UserDetailsDto updateUser(String username, UserDetailsDto userDetailsDto) {
        User existingUser = userService.getUserByUsername(username);

        if (username.equals(existingUser.getUsername())) {
            throw new UserAlreadyExistsException(username);
        }

        return userService.updateUser(existingUser, userDetailsDto);
    }

    public void deleteUser(String username) {
        userService.deleteUser(username);
    }

}
