package com.example.Application.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDataService {
    private  final JdbcTemplate jdbc;
    public UserDataService(JdbcTemplate jdbc){this.jdbc=jdbc;

    }

    public User findByUserName(String us){
        //System.out.println("AAAAAAAAAAAAAAAAAAAAAMNNNNNNNNNNNNNNNNNNNNNNNNA");
        return jdbc.queryForObject("select * from users where username=?",User.class,us);

    }
}
