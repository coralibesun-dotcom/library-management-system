package com.coraline.library.mapper;

import com.coraline.library.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OperationLogMapper {

    // 新增日志
    int insert(OperationLog log);



    // 分页查询日志
    List<OperationLog> findPage(
            Integer offset,
            Integer pageSize
    );

    // 查询日志总数量
    int count();

    // 根据用户查询
    List<OperationLog> findByUserId(Long userId);


    // 根据操作类型查询
    List<OperationLog> findByOperation(String operation);


    // 按时间范围查询
    List<OperationLog> findByTimeRange(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

}
