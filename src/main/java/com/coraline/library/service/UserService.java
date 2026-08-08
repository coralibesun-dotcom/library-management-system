package com.coraline.library.service;

import com.coraline.library.common.PageResult;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.dto.UserQueryDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.vo.LoginVO;
import com.coraline.library.vo.UserVO;

public interface UserService {

    //注册
    void register(UserRegisterDTO dto);


    // 登录业务
    LoginVO login(UserLoginDTO dto);;

    // 查询当前登录用户
    LoginVO findCurrentUser();

    // 管理员查看用户
    PageResult<UserVO> findPage(UserQueryDTO dto);


    // 修改状态
    void updateStatus(
            Long id,
            Integer status
    );


    // 修改角色
    void updateRole(
            Long id,
            String role
    );

}
