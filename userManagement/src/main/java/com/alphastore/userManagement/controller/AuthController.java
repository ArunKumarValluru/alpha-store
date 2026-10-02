package com.alphastore.userManagement.controller;

import com.alphastore.userManagement.constants.APIConstants;
import com.alphastore.userManagement.interfaces.IAuthService;
import com.alphastore.userManagement.dto.UserDetailsDTO;
import com.alphastore.utils.pojo.APIResponse;
import com.alphastore.utils.service.APIResponseService;
import com.alphastore.utils.validation.ValidationUtils;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final APIResponseService apiResponseService;
    private final IAuthService authService;

    AuthController( APIResponseService apiResponseService,
                    IAuthService authService) {
        this.apiResponseService = apiResponseService;
        this.authService = authService;
    }

    @PostMapping(APIConstants.REGISTER_USER)
    public APIResponse registerUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        logger.info(":: registerUser :: email={}, role={}", userDetailsDTO.getEmail(), userDetailsDTO.getRole());

        if (!ValidationUtils.isValidEmail(userDetailsDTO.getEmail())) {
            return apiResponseService.buildAPIResponse(HttpStatus.BAD_REQUEST, "invalid email received");
        }
        if (!ValidationUtils.isValidPassword(userDetailsDTO.getPassword())) {
            return apiResponseService.buildAPIResponse(HttpStatus.BAD_REQUEST, "password must be 8-64 characters with uppercase, lowercase, digit and special character");
        }
        if (ValidationUtils.isNullOrEmpty(userDetailsDTO.getRole())) {
            return apiResponseService.buildAPIResponse(HttpStatus.BAD_REQUEST, "invalid role received");
        }
        if (ValidationUtils.isNullOrEmpty(userDetailsDTO.getFirstName())) {
            return apiResponseService.buildAPIResponse(HttpStatus.BAD_REQUEST, "invalid firstName received");
        }

        try {
            return authService.registerUser(userDetailsDTO.getEmail(), userDetailsDTO.getPassword(), userDetailsDTO.getRole(), userDetailsDTO.getFirstName(), userDetailsDTO.getLastName());
        } catch (DataIntegrityViolationException e) {
            logger.info(":: registerUser :: duplicate data for email={}", userDetailsDTO.getEmail());
            return apiResponseService.buildAPIResponse(HttpStatus.CONFLICT, "user already exists with this email");
        } catch (Exception e) {
            logger.error(":: registerUser :: failed for email={}", userDetailsDTO.getEmail(), e);
            return apiResponseService.buildAPIResponse(HttpStatus.INTERNAL_SERVER_ERROR, "failed to register user");
        }
    }
}
