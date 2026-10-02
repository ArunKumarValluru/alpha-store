package com.alphastore.userManagement.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_role_mapping")
public class UserRoleMapping {

    @Id
    @Column(name = "user_role_uuid")
    private UUID userRoleUUID;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUUID;

    @Column(name = "role_uuid", nullable = false)
    private UUID roleUUID;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;
}
