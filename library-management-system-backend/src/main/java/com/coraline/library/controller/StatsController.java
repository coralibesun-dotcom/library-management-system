package com.coraline.library.controller;


import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.StatsVO;
import com.coraline.library.mapper.StatsMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页统计
 */
@RestController
@RequestMapping("/stats")
public class StatsController {


    private final StatsMapper statsMapper;


    public StatsController(
            StatsMapper statsMapper
    ){

        this.statsMapper = statsMapper;

    }


    /**
     * 首页 Dashboard 统计数据
     * 登录用户都能看（USER、ADMIN）
     */
    @RequireRole({"USER", "ADMIN"})
    @GetMapping
    public Result<StatsVO> getStats(){

        return Result.success(
                statsMapper.getStats()
        );

    }

}
