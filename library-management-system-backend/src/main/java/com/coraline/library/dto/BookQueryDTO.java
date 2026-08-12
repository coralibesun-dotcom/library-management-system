package com.coraline.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Data
public class BookQueryDTO {


    /**
     * 当前页
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;


    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量必须大于0")
    @Max(value = 20, message = "每页最多查询20条")
    private Integer pageSize = 10;


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
     * 0 草稿
     * 1 上架
     * 2 下架
     */
    private Integer status;

}
