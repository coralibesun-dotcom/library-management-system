package com.coraline.library.mapper;


import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OperationLogMapper {


    // 新增日志
    int insert(OperationLog log);


    // 分页查询日志（支持条件）
    List<OperationLog> findPage(
            @Param("query") OperationLogQueryDTO query,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );


    // 查询日志数量
    int count(OperationLogQueryDTO query);

}
