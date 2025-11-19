package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Modal.AddressDTO.Address;
import com.ajlearn.akecommerce.Modal.User.UserPrincipal;
import com.ajlearn.akecommerce.Repo.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    public List<Address> findAddresses(Long id) {
        return addressRepo.find(id);
    }

    public Address findAddress(Long id, Long addressId) {
        return addressRepo.findById(id,addressId);
    }

    public int addAddress(Long id, Address address) {
        return addressRepo.add(id,address);
    }

    public int updateAddress(Long id, Address address) {
        return addressRepo.update(id,address);
    }

    public int deleteAddress(Long id, Long addressId) {
        return addressRepo.delete(id,addressId);
    }
}
