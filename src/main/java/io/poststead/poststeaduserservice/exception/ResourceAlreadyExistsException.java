package io.poststead.poststeaduserservice.exception;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
