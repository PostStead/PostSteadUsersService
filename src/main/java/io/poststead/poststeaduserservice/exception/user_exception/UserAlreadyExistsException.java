package io.poststead.poststeaduserservice.exception.user_exception;

import io.poststead.poststeaduserservice.exception.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(String name) {
        super("User with username \"" + name + "\" already exists!");
    }
}
