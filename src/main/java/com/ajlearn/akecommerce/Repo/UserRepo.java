package com.ajlearn.akecommerce.Repo;

import com.ajlearn.akecommerce.Modal.DAO.UserInfoUpdate;
import com.ajlearn.akecommerce.Modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;


    // ✅ Save user
    public int save(User user) {
        String sql = "insert into users (username,email,password,role,firstname,lastname,phonenumber," +
                "isemailverified,isphoneverified) values (:username,:email,:password,:role," +
                ":firstName,:lastName,:phoneNumber,:emailVerified,:phoneVerified)";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        return namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        List<User> users = namedParameterJdbcTemplate.query(
                sql,
                params,
                new BeanPropertyRowMapper<>(User.class)
        );

        return users.isEmpty() ? null : users.get(0);
    }


    public int updateUserInfo(String username, UserInfoUpdate user) {
        String sql = "UPDATE users SET " +
                "email = :email, " +
                "firstname=:firstName," +
                "lastname=:lastName," +
                "phonenumber=:phoneNumber," +
                "isactive=:active," +
                "updatedat = NOW() " +
                "WHERE username = :username";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("phoneNumber", user.getPhoneNumber())
                .addValue("active", user.isActive())
                .addValue("username", username);

        return namedParameterJdbcTemplate.update(sql, params);
    }

    public int updateLastLogin(String username) {
        String sql = "UPDATE users SET lastloginat = NOW() WHERE username = :username";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("username",username);
        return namedParameterJdbcTemplate.update(sql,params);
    }


}
