package com.ajlearn.akecommerce.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

}
