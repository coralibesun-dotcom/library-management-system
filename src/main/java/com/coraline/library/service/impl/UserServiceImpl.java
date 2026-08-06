package com.coraline.library.service.impl;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.common.enums.UserStatusEnum;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.entity.User;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.UserMapper;
import com.coraline.library.service.UserService;
import com.coraline.library.utils.JwtUtil;
import com.coraline.library.vo.LoginVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;


    public UserServiceImpl(
            UserMapper userMapper,
            BCryptPasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {

        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;

    }

    /**
     * 注册
     */
    @Override
    public void register(UserRegisterDTO dto) {

        // ===== 参数校验 =====
        if(dto.getUsername() == null
                || dto.getUsername().trim().isEmpty()){

            throw new BusinessException(
                    ResultCodeEnum.PARAM_ERROR,
                    "用户名不能为空"
            );

        }


        if(dto.getPassword() == null
                || dto.getPassword().trim().isEmpty()){

            throw new BusinessException(
                    ResultCodeEnum.PARAM_ERROR,
                    "密码不能为空"
            );

        }

        User exist =
                userMapper.findByUsername(
                        dto.getUsername()
                );


        if(exist != null){

            throw new BusinessException(
                    ResultCodeEnum.USER_EXIST,
                    "用户名已存在"
            );

        }


        User user = new User();


        user.setUsername(
                dto.getUsername()
        );


        user.setPassword(
                passwordEncoder.encode(
                        dto.getPassword()
                )
        );


        // 默认普通用户
        user.setRole("USER");


        // 默认启用
        user.setStatus(
                UserStatusEnum.ENABLE.getCode()
        );


        userMapper.insert(user);

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


        LoginVO vo = convertToVO(user);

        String token =
                jwtUtil.createToken(
                        user.getId(),
                        user.getUsername(),
                        user.getRole()
                );

        vo.setToken(token);

        return vo;

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
