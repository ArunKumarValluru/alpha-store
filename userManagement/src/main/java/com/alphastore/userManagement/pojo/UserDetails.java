package com.alphastore.userManagement.pojo;

import lombok.Data;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserDetails {

    @Id
    @Column(name = "user_uuid")
    private UUID userID;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "mobile_number", length = 20)
    private String mobileNumber;

    @Column(name = "email_id", nullable = false)
    private String emailID;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "loc_uuid")
    private UUID locUUID;

    // jsonb column, kept as a JSON string; the cast lets Postgres accept it
    @Column(name = "info_json", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String infoJson;

    @Column(name = "insert_ts", nullable = false)
    private LocalDateTime insertTs;

    @Column(name = "update_ts", nullable = false)
    private LocalDateTime updateTs;

    @Column(name = "active_user", nullable = false)
    private Boolean activeUser;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;
}
