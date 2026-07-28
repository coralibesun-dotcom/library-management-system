package com.coraline.library.dto;

import lombok.Data;


@Data
public class BookQueryDTO {


    /**
     * 当前页
     */
    private Integer pageNum;


    /**
     * 每页数量
     */
    private Integer pageSize;


    /**
     * 搜索关键词
     * 可以搜索书名、作者等
     */
    private String keyword;


    /**
     * 分类id
     */
    private Long categoryId;


    /**
     * 图书状态
     * 例如：
     * 1 上架
     * 0 下架
     */
    private Integer status;

}
