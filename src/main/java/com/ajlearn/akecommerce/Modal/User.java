package com.ajlearn.akecommerce.Modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImageUrl;

    private String role;// e.g. USER, ADMIN
    private boolean active;
    private boolean emailVerified;
    private boolean phoneVerified;

    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}