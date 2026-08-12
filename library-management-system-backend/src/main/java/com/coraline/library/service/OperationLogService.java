package com.coraline.library.service;

import com.coraline.library.common.PageResult;
import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;


public interface OperationLogService {


    void record(OperationLog log);


    PageResult<OperationLog> findPage(
            OperationLogQueryDTO query
    );

}
