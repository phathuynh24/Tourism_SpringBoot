package com.tourism.backend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    // private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePicture;
}
