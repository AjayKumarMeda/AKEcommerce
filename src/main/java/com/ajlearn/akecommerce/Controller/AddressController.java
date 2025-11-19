package com.ajlearn.akecommerce.Controller;

import com.ajlearn.akecommerce.Modal.AddressDTO.Address;
import com.ajlearn.akecommerce.Modal.User.UserPrincipal;
import com.ajlearn.akecommerce.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/all")
    public List<Address> findAllAddress(Authentication authentication){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return addressService.findAddresses(principal.getId());
    }

    @GetMapping("/addressInfo")
    public Address findByAddressId(Authentication authentication, @RequestParam Long addressId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return addressService.findAddress(principal.getId(),addressId);
    }

    @PostMapping("/add")
    public String addUserAddress(Authentication authentication, @RequestBody Address address){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = addressService.addAddress(principal.getId(),address);
        if (rows > 0)
            return "Address Added successfully";
        else
            return "Failed to Add Address...";
    }

    @PutMapping("/update")
    public String updateUserAddress(Authentication authentication, @RequestBody Address address){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = addressService.updateAddress(principal.getId(),address);
        if (rows > 0)
            return "Address Updated Successfully.";
        else
            return "Failed to Update the Address......";
    }

    @DeleteMapping("/delete")
    public String deleteUserAddress(Authentication authentication, @RequestParam Long addressId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        int rows = addressService.deleteAddress(principal.getId(),addressId);
        if (rows > 0)
            return "Address Successfully Deleted.";
        else
            return "Failed to delete the address......";
    }
}
