package com.coraline.library.entity;

import lombok.Data;

@Data
public class Category {

  private long id;
  private String name;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
