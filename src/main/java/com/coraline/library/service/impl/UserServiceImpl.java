package com.coraline.library.service.impl;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.entity.User;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.UserMapper;
import com.coraline.library.service.UserService;
import com.coraline.library.vo.LoginVO;
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
    public LoginVO login(UserLoginDTO dto) {


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


        return convertToVO(user);

    }



    /**
     * 根据id查询用户
     */
    @Override
    public LoginVO findById(Long id) {


        User user = userMapper.findById(id);


        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }


        return convertToVO(user);

    }

    /**
     * Entity转换VO
     */
    private LoginVO convertToVO(User user){


        LoginVO vo = new LoginVO();


        vo.setId(user.getId());


        vo.setUsername(
                user.getUsername()
        );


        vo.setRole(
                user.getRole()
        );



        return vo;

    }



}
