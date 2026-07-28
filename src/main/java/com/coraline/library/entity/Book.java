package com.coraline.library.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {

  private Long id;

  private String name;

  private String author;

  private String isbn;

  private Long categoryId;

  private String publisher;

  private Long stock;

  private String description;

  private Integer status;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

}
