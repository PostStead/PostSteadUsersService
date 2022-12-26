package io.poststead.poststeaduserservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserAuthDto {

    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    private String password;

}
