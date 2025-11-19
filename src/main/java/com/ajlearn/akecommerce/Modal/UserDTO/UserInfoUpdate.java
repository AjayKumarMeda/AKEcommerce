package com.ajlearn.akecommerce.Modal.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoUpdate {

    private String username;      // optional — only if you allow changing username
    private String email;
    private String password;      // optional — only if you handle password change securely
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profileImageUrl;
    private String role;
    private boolean active;
    private boolean emailVerified;
    private boolean phoneVerified;


}
