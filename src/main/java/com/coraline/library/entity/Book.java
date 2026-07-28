package com.coraline.library.entity;

import lombok.Data;

@Data
public class Book {

  private long id;
  private String name;
  private String author;
  private String isbn;
  private long categoryId;
  private String publisher;
  private long stock;
  private String description;
  private long status;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;



}
