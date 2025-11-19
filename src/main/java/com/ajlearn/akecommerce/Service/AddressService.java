package com.ajlearn.akecommerce.Service;

import com.ajlearn.akecommerce.Repo.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;
}
