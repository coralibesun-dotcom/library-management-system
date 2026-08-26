package com.coraline.library.dto;

import lombok.Data;

/**
 * 首页 Dashboard 统计数据
 */
@Data
public class StatsVO {

    /** 图书种类数（book 表行数） */
    private Long bookCount;

    /** 馆藏总库存（SUM(stock)） */
    private Long totalStock;

    /** 当前在借数（borrow_record status=0） */
    private Long borrowingCount;

    /** 已归还数（status=1） */
    private Long returnedCount;

    /** 逾期数（status=2） */
    private Long overdueCount;

    /** 今日借出数（borrow_time = 今天） */
    private Long todayBorrow;

    /** 今日归还数（return_time = 今天） */
    private Long todayReturn;

    /** 用户总数 */
    private Long userCount;

    /** 分类总数 */
    private Long categoryCount;

}
