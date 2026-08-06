package com.coraline.library.mapper;

import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //注册用户
    int insert(User user);

    // 根据账号查询，用于登录
    User findByUsername(String username);

    // 查询当前登录用户信息
    User findById(Long id);

}
