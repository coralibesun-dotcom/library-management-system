package com.coraline.library.service.impl;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.entity.User;
import com.coraline.library.exception.BusinessException;
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
    public User login(UserLoginDTO dto) {


        User user =
                userMapper.findByUsername(
                        dto.getUsername()
                );


        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }


        boolean result =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );


        if(!result){

            throw new BusinessException(
                    ResultCodeEnum.PASSWORD_ERROR,
                    "密码错误"
            );

        }


        return user;

    }



    /**
     * 根据id查询用户
     */
    @Override
    public User findById(Long id) {


        User user = userMapper.findById(id);


        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }


        return user;

    }


}
