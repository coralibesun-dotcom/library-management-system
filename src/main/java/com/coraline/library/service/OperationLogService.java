package com.coraline.library.service;

import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;

import java.util.List;

public interface OperationLogService {


    void record(OperationLog log);


    List<OperationLog> findPage(
            OperationLogQueryDTO query
    );


    int count(
            OperationLogQueryDTO query
    );

}
