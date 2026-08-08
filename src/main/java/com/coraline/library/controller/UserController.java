package com.coraline.library.controller;


import com.coraline.library.common.Result;
import com.coraline.library.dto.UserLoginDTO;
import com.coraline.library.dto.UserRegisterDTO;
import com.coraline.library.service.UserService;
import com.coraline.library.vo.LoginVO;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {

        this.userService = userService;

    }


    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(
            @RequestBody UserRegisterDTO dto
    ){

        userService.register(dto);

        return Result.success();

    }


    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(
            @RequestBody UserLoginDTO dto
    ){

        return Result.success(
                userService.login(dto)
        );

    }


    /**
     * 查询当前登录用户信息
     */
    @GetMapping("/info")
    public Result<LoginVO> info(){

        return Result.success(
                userService.findCurrentUser()
        );

    }


}
