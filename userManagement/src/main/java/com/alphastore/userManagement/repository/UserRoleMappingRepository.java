package com.alphastore.userManagement.repository;

import com.alphastore.userManagement.pojo.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, UUID> {
}
