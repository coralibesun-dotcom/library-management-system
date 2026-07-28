package com.coraline.library.service;

import com.coraline.library.entity.User;

public interface UserService {
    // 登录业务
    User login(String username,String password);

    // 根据id查询用户信息
    User findById(Long id);

}
