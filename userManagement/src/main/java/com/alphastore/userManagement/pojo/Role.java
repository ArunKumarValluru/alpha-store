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
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "role_uuid")
    private UUID roleUUID;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;

    @Column(name = "updated_ts", nullable = false)
    private LocalDateTime updatedTs;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
