package com.alphastore.userManagement.repository;

import com.alphastore.userManagement.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    // only roles that are usable: active and not soft-deleted
    Role findByRoleNameIgnoreCaseAndActiveTrueAndDeletedFalse(String roleName);

}
