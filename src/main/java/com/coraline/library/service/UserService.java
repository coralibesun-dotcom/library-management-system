package com.coraline.library.service;

import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.vo.LoginVO;

public interface UserService {

    //注册
    void register(UserRegisterDTO dto);


    // 登录业务
    LoginVO login(UserLoginDTO dto);;

    // 根据id查询用户信息
    LoginVO findById(Long id);

}
