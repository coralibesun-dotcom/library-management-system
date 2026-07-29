package com.coraline.library.controller;


import com.coraline.library.common.Result;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.entity.User;
import com.coraline.library.service.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;

    }



    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(
            @RequestBody UserLoginDTO dto
    ){

        return Result.success(
                userService.login(dto)
        );

    }


    /**
     * 根据id查询用户
     */
    @GetMapping("/{id}")
    public Result<User> findById(
            @PathVariable("id") Long id
    ){

        return Result.success(
                userService.findById(id)
        );

    }

}
