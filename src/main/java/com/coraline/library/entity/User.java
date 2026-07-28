package com.coraline.library.entity;

import lombok.Data;

@Data
public class User {

  private long id;
  private String username;
  private String password;
  private String role;
  private long status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
