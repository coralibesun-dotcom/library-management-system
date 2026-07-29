package com.coraline.library.service.impl;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.OperationLogMapper;
import com.coraline.library.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperationLogServiceImpl implements OperationLogService {


    private final OperationLogMapper operationLogMapper;


    public OperationLogServiceImpl(
            OperationLogMapper operationLogMapper
    ){

        this.operationLogMapper = operationLogMapper;

    }



    /**
     * 保存日志
     */
    @Override
    public void record(OperationLog log) {


        int result = operationLogMapper.insert(log);


        if(result == 0){

            throw new BusinessException(
                    ResultCodeEnum.SYSTEM_ERROR,
                    "日志保存失败"
            );

        }

    }



    /**
     * 分页查询日志
     */
    @Override
    public List<OperationLog> findPage(
            OperationLogQueryDTO query
    ){

        return operationLogMapper.findPage(query);

    }



    /**
     * 查询日志数量
     */
    @Override
    public int count(
            OperationLogQueryDTO query
    ){

        return operationLogMapper.count(query);

    }

}
