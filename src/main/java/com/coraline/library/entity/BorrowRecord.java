package com.coraline.library.entity;

import lombok.Data;

@Data
public class BorrowRecord {

  private long id;
  private long userId;
  private long bookId;
  private java.sql.Timestamp borrowTime;
  private java.sql.Timestamp returnTime;
  private long status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
