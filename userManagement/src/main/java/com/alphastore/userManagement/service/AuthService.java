package com.alphastore.userManagement.service;

import com.alphastore.userManagement.interfaces.IAuthService;
import com.alphastore.userManagement.pojo.Role;
import com.alphastore.userManagement.pojo.UserDetails;
import com.alphastore.userManagement.pojo.UserRoleMapping;
import com.alphastore.userManagement.repository.RoleRepository;
import com.alphastore.userManagement.repository.UserRepository;
import com.alphastore.userManagement.repository.UserRoleMappingRepository;
import com.alphastore.utils.pojo.APIResponse;
import com.alphastore.utils.service.APIResponseService;
import com.alphastore.utils.uuid.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService implements IAuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final APIResponseService apiResponseService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleMappingRepository userRoleMappingRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    AuthService(APIResponseService apiResponseService,
                UserRepository userRepository,
                RoleRepository roleRepository,
                UserRoleMappingRepository userRoleMappingRepository) {
        this.apiResponseService = apiResponseService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleMappingRepository = userRoleMappingRepository;
    }

    @Override
    @Transactional
    public APIResponse registerUser(String email, String password, String role, String firstName, String lastName) {
        logger.info(":: registerUser :: email={}, role={}", email, role);

        String emailID = email.trim().toLowerCase();
        if (userRepository.existsByEmailID(emailID)) {
            return apiResponseService.buildAPIResponse(HttpStatus.CONFLICT, "user already exists with this email");
        }

        Role userRole = roleRepository.findByRoleNameIgnoreCaseAndActiveTrueAndDeletedFalse(role.trim());
        if (userRole == null || userRole.getRoleUUID() == null) {
            return apiResponseService.buildAPIResponse(HttpStatus.BAD_REQUEST, "invalid role received");
        }

        LocalDateTime now = LocalDateTime.now();
        UserDetails userDetails = new UserDetails();
        userDetails.setUserID(UUIDUtils.generateUUID());
        userDetails.setEmailID(emailID);
        userDetails.setPasswordHash(passwordEncoder.encode(password));
        userDetails.setFirstName(firstName.trim());
        userDetails.setLastName(lastName);
        userDetails.setActiveUser(true);
        userDetails.setDeleted(false);
        userDetails.setInsertTs(now);
        userDetails.setUpdateTs(now);
        userRepository.save(userDetails);

        UserRoleMapping userRoleMapping = new UserRoleMapping();
        userRoleMapping.setUserRoleUUID(UUIDUtils.generateUUID());
        userRoleMapping.setUserUUID(userDetails.getUserID());
        userRoleMapping.setRoleUUID(userRole.getRoleUUID());
        userRoleMapping.setCreatedTs(now);
        userRoleMappingRepository.save(userRoleMapping);

        logger.info(":: registerUser :: user registered, userID={}", userDetails.getUserID());

        return apiResponseService.buildAPIResponse( HttpStatus.CREATED, "registered successfully");
    }
}
