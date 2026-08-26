package com.coraline.library.mapper;

import com.coraline.library.dto.StatsVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页统计 Mapper
 * 一条 SQL 用子查询把所有聚合数算出来，一次往返拿全
 */
@Mapper
public interface StatsMapper {

    StatsVO getStats();

}
