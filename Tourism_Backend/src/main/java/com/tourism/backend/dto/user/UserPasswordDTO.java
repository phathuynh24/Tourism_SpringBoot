package com.tourism.backend.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDTO {

    @NotNull(message = "New password cannot be null")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}
