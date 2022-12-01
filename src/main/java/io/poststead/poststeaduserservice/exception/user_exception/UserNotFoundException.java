package io.poststead.poststeaduserservice.exception.user_exception;

import io.poststead.poststeaduserservice.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String username) {
        super("User with username " + username + " was not found!");
    }

}
