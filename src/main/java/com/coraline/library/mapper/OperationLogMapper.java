package com.coraline.library.mapper;


import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OperationLogMapper {


    // 新增日志
    int insert(OperationLog log);


    // 分页查询日志（支持条件）
    List<OperationLog> findPage(OperationLogQueryDTO query);


    // 查询日志数量
    int count(OperationLogQueryDTO query);

}
