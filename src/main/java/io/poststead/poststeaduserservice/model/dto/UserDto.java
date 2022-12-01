package io.poststead.poststeaduserservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor()
public class UserDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String password;

}
