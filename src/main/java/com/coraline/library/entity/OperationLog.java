package com.coraline.library.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {

  private Long id;

  private Long userId;

  private String operation;

  private String target;

  private LocalDateTime createTime;

}
