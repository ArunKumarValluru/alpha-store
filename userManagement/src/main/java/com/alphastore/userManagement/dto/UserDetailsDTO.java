package com.alphastore.userManagement.dto;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private String email;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
}
