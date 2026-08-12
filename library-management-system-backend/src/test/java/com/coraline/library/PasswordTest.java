package com.coraline.library;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordTest {


    @Test
    public void createPassword(){


        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();


        String password =
                encoder.encode("123456");


        System.out.println(password);

    }

}
