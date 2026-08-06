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
    @PostMapping("/register")   // 新增
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
     * 根据id查询用户
     */
    @GetMapping("/{id}")
    public Result<LoginVO> findById(
            @PathVariable("id") Long id
    ){

        return Result.success(
                userService.findById(id)
        );

    }

}
