package io.poststead.poststeaduserservice.controller;

import io.poststead.poststeaduserservice.model.dto.UserAuthDto;
import io.poststead.poststeaduserservice.model.dto.UserDetailsDto;
import io.poststead.poststeaduserservice.service.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
class UserController {

    private final UserFacade userFacade;

    private final RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable String username) {
        ResponseEntity<UserDetailsDto> result = ResponseEntity.ok(userFacade.getUserByUsername(username));
        sendRabbitMessage(result.toString());
        return result;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@RequestBody UserAuthDto user) {
        ResponseEntity<Void> result = ResponseEntity.created(userFacade.addUser(user)).build();
        sendRabbitMessage(result.toString());
        return result;
    }

    @PreAuthorize("principal.username == #username")
    @PutMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDto> updateUser(
            @PathVariable String username,
            @RequestBody UserDetailsDto userDetailsDto
    ) {
        ResponseEntity<UserDetailsDto> result = ResponseEntity.ok(userFacade.updateUser(username, userDetailsDto));
        sendRabbitMessage(result.toString());
        return result;
    }

    @PreAuthorize("principal.username == #username")
    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userFacade.deleteUser(username);
        ResponseEntity<Void> result = ResponseEntity.noContent().build();
        sendRabbitMessage(result.toString());
        return result;
    }

    private void sendRabbitMessage(String event) {
        rabbitTemplate.convertAndSend("io.poststead.exchange", "io.poststead.audit", event);
    }
}
