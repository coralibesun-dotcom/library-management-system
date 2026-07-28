package com.coraline.library.entity;

import lombok.Data;

@Data
public class OperationLog {

  private long id;
  private long userId;
  private String operation;
  private String target;
  private java.sql.Timestamp createTime;

}
