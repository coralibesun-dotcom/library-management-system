package com.coraline.library.controller;


import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.UserQueryDTO;
import com.coraline.library.service.UserService;
import com.coraline.library.vo.UserVO;
import com.coraline.library.common.PageResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/user")
@RequireRole("ADMIN")
public class AdminUserController {


    private final UserService userService;


    public AdminUserController(
            UserService userService
    ){

        this.userService = userService;

    }



    /**
     * 管理员分页查看用户
     *
     * GET
     * /admin/user/page
     *
     * 示例：
     * /admin/user/page?pageNum=1&pageSize=10
     */
    @GetMapping("/page")
    public Result<PageResult<UserVO>> findPage(
            UserQueryDTO dto
    ){

        return Result.success(
                userService.findPage(dto)
        );

    }





    /**
     * 修改用户状态
     *
     * 启用/禁用
     *
     * PUT
     * /admin/user/status
     *
     * 参数：
     * id=5
     * status=0
     */
    @PutMapping("/status")
    public Result<Void> updateStatus(
            @RequestParam Long id,
            @RequestParam Integer status
    ){


        userService.updateStatus(
                id,
                status
        );


        return Result.success();

    }





    /**
     * 修改用户角色
     *
     * PUT
     * /admin/user/role
     *
     * 参数：
     * id=5
     * role=ADMIN
     */
    @PutMapping("/role")
    public Result<Void> updateRole(
            @RequestParam Long id,
            @RequestParam String role
    ){


        userService.updateRole(
                id,
                role
        );


        return Result.success();

    }


}
