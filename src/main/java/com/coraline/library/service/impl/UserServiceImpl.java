package com.coraline.library.service.impl;


import com.coraline.library.entity.User;
import com.coraline.library.mapper.UserMapper;
import com.coraline.library.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder
    ) {

        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

    }



    /**
     * 登录
     */
    @Override
    public User login(String username, String password) {


        // 1. 根据用户名查询用户
        User user = userMapper.findByUsername(username);


        if(user == null){

            throw new RuntimeException("用户不存在");

        }


        // 2. 校验密码
        boolean result = passwordEncoder.matches(
                password,
                user.getPassword()
        );


        if(!result){

            throw new RuntimeException("密码错误");

        }


        // 3. 返回用户信息
        return user;

    }



    /**
     * 根据id查询用户
     */
    @Override
    public User findById(Long id) {


        User user = userMapper.findById(id);


        if(user == null){

            throw new RuntimeException("用户不存在");

        }


        return user;

    }


}
