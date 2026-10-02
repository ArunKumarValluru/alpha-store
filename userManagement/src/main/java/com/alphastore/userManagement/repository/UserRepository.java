package com.alphastore.userManagement.repository;

import com.alphastore.userManagement.pojo.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, UUID> {

    Optional<UserDetails> findByEmailID(String emailID);

    boolean existsByEmailID(String emailID);
}
