package com.coraline.library.service.impl;


import com.coraline.library.common.PageResult;
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



    @Override
    public PageResult<OperationLog> findPage(
            OperationLogQueryDTO query
    ){


        if(query.getPageNum() == null){
            query.setPageNum(1);
        }


        if(query.getPageSize() == null){
            query.setPageSize(10);
        }


        Integer offset =
                (query.getPageNum()-1)
                        * query.getPageSize();



        List<OperationLog> records =
                operationLogMapper.findPage(
                        query,
                        offset,
                        query.getPageSize()
                );


        int total =
                operationLogMapper.count(query);



        return new PageResult<>(
                records,
                (long) total
        );

    }

}
