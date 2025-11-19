package com.ajlearn.akecommerce.Modal.AddressDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Address {

    private Long id;
    private String fullName;
    private String phone;

    private String address;
    private String pincode;
    private String street;
    private String city;
    private String state;

    private String addressType;
    private Boolean isDefault;

}