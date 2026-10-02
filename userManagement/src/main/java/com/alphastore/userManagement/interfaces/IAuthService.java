package com.alphastore.userManagement.interfaces;

import com.alphastore.utils.pojo.APIResponse;

public interface IAuthService {
    APIResponse registerUser(String email, String password, String role, String firstName, String lastName);
}
