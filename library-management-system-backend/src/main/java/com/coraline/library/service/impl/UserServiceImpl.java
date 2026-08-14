package com.coraline.library.service.impl;


import com.coraline.library.common.PageResult;
import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.context.UserContext;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.common.enums.RoleEnum;
import com.coraline.library.common.enums.UserStatusEnum;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.dto.UserQueryDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.entity.User;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.UserMapper;
import com.coraline.library.service.UserService;
import com.coraline.library.utils.JwtUtil;
import com.coraline.library.vo.LoginVO;
import com.coraline.library.vo.UserVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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
    @Log("用户注册")
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
                    ResultCodeEnum.LOGIN_ERROR
            );

        }


        if(UserStatusEnum.DISABLE.getCode()
                .equals(user.getStatus())){


            throw new BusinessException(
                    ResultCodeEnum.USER_DISABLE,
                    "账号已被禁用"
            );

        }


        boolean result =
                passwordEncoder.matches(
                        dto.getPassword(),
                        user.getPassword()
                );


        if(!result){

            throw new BusinessException(
                    ResultCodeEnum.LOGIN_ERROR
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
    public LoginVO findCurrentUser() {


        // 从JWT上下文获取当前用户id
        Long userId =
                UserContext.getUserId();


        User user =
                userMapper.findById(userId);



        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }

        if(UserStatusEnum.DISABLE.getCode()
                .equals(user.getStatus())){


            throw new BusinessException(
                    ResultCodeEnum.USER_DISABLE,
                    "账号已被禁用"
            );

        }

        return convertToVO(user);

    }




    /**
     * 管理员查看全部用户
     */
    @Override
    public PageResult<UserVO> findPage(UserQueryDTO dto) {


        int offset =
                (dto.getPageNum() - 1)
                        * dto.getPageSize();



        List<User> users =
                userMapper.findPage(
                        dto,
                        offset
                );


        Long total =
                userMapper.count(dto);



        List<UserVO> list =
                users.stream()
                        .map(this::convertToUserVO)
                        .toList();



        return new PageResult<>(
                list,
                total
        );

    }



    /**
     * 修改用户状态
     *
     * 例如：
     * 1 启用
     * 0 禁用
     */
    @Log("修改用户状态")
    @Override
    public void updateStatus(
            Long id,
            Integer status
    ) {


        User user =
                userMapper.findById(id);


        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }

        if(status == null
                ||
                (!status.equals(UserStatusEnum.ENABLE.getCode())
                        &&
                        !status.equals(UserStatusEnum.DISABLE.getCode()))){

            throw new BusinessException(
                    ResultCodeEnum.PARAM_ERROR,
                    "用户状态错误"
            );

        }


        Long currentUserId =
                UserContext.getUserId();


        if(currentUserId.equals(id)
                &&
                UserStatusEnum.DISABLE.getCode()
                        .equals(status)){

            throw new BusinessException(
                    ResultCodeEnum.FORBIDDEN,
                    "不能禁用自己的账号"
            );

        }

        userMapper.updateStatus(
                id,
                status
        );

    }





    /**
     * 修改用户角色
     *
     * USER
     * ADMIN
     */
    @Log("修改用户角色")
    @Override
    public void updateRole(
            Long id,
            String role
    ) {


        User user =
                userMapper.findById(id);


        if(user == null){

            throw new BusinessException(
                    ResultCodeEnum.USER_NOT_FOUND,
                    "用户不存在"
            );

        }

        // 校验角色是否合法，防止前端传入不存在的角色，例如 ROOT、SUPER_ADMIN
        boolean valid = false;


        for(RoleEnum roleEnum : RoleEnum.values()){


            if(roleEnum.name().equals(role)){

                valid = true;
                break;

            }

        }



        if(!valid){

            throw new BusinessException(
                    ResultCodeEnum.PARAM_ERROR,
                    "角色不存在"
            );

        }

       // 当前登录用户
        Long currentUserId =
                UserContext.getUserId();


        // 防止管理员修改自己的角色
        if(currentUserId.equals(id)){

            throw new BusinessException(
                    ResultCodeEnum.FORBIDDEN,
                    "不能修改自己的角色"
            );

        }
        userMapper.updateRole(
                id,
                role
        );

    }

    @Override
    public void logout() {
        // JWT 无状态认证，服务端无需处理
    }


    /**
     * Entity转换VO
     */

    //UserVO
    private UserVO convertToUserVO(User user){


        UserVO vo = new UserVO();


        vo.setId(
                user.getId()
        );


        vo.setUsername(
                user.getUsername()
        );


        vo.setRole(
                user.getRole()
        );


        vo.setStatus(
                user.getStatus()
        );


        vo.setCreateTime(
                user.getCreateTime()
        );


        return vo;

    }


    //LoginVO
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
