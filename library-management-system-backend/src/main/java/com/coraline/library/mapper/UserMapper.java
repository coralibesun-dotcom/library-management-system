package com.coraline.library.mapper;

import com.coraline.library.dto.UserQueryDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    //注册用户
    int insert(User user);

    // 根据账号查询，用于登录
    User findByUsername(String username);

    // 查询当前登录用户信息
    User findById(Long id);

    // 分页可筛选查询全部用户
    List<User> findPage(
            @Param("dto") UserQueryDTO dto,
            @Param("offset") Integer offset
    );

    //统计用户数量
    Long count(
            @Param("dto") UserQueryDTO dto
    );

    // 修改用户状态
    int updateStatus(
            @Param("id") Long id,
            @Param("status") Integer status
    );


    // 修改用户角色
    int updateRole(
            @Param("id") Long id,
            @Param("role") String role
    );

}
