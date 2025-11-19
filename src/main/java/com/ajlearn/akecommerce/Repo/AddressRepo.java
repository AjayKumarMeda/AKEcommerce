package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.AddressDTO.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Address> find(Long id) {
        String sql = "select * from addresses where user_id = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",id);

        return namedParameterJdbcTemplate.query(sql,params,
                new BeanPropertyRowMapper<>(Address.class));
    }

    public Address findById(Long id, Long addressId) {
        String sql = "select * from addresses where user_id = :userId and id = :addressId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",id)
                .addValue("addressId",addressId);

        return namedParameterJdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<>(Address.class));
    }

    public int add(Long id, Address address) {
        String sql = "INSERT INTO addresses (user_id, full_name, phone, address, pincode, street, city, state, address_type, is_default) " +
                "VALUES (:userId, :fullName, :phone, :address, :pincode, :street, :city, :state, :addressType, :isDefault)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",id)
                .addValue("fullName",address.getFullName())
                .addValue("phone",address.getPhone())
                .addValue("address",address.getAddress())
                .addValue("pincode",address.getPincode())
                .addValue("street",address.getStreet())
                .addValue("city",address.getCity())
                .addValue("state",address.getState())
                .addValue("addressType",address.getAddressType())
                .addValue("isDefault",address.getIsDefault());

        return namedParameterJdbcTemplate.update(sql,params);
    }

    public int update(Long id, Address address) {
        String sql = "UPDATE addresses SET full_name=:fullName, phone=:phone, address=:address, " +
                "pincode=:pincode, street=:street, city=:city, " +
                "state=:state, address_type=:addressType, is_default=:isDefault " +
                "WHERE user_id= :userId and id=:addressId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",id)
                .addValue("addressId",address.getId())
                .addValue("fullName",address.getFullName())
                .addValue("phone",address.getPhone())
                .addValue("address",address.getAddress())
                .addValue("pincode",address.getPincode())
                .addValue("street",address.getStreet())
                .addValue("city",address.getCity())
                .addValue("state",address.getState())
                .addValue("addressType",address.getAddressType())
                .addValue("isDefault",address.getIsDefault());

        return namedParameterJdbcTemplate.update(sql,params);
    }

    public int delete(Long id, Long addressId) {
        String sql = "delete from addresses where user_id = :userId and id = :addressId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId",id)
                .addValue("addressId",addressId);
        return namedParameterJdbcTemplate.update(sql,params);
    }
}
