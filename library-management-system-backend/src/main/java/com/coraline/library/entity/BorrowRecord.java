package com.coraline.library.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowRecord {


  private Long id;


  private Long userId;


  private Long bookId;


  private LocalDateTime borrowTime;


  private LocalDateTime returnTime;


  private Integer status;


  private LocalDateTime createTime;


  private LocalDateTime updateTime;

}
