package com.coraline.library.vo;

import com.coraline.library.entity.BorrowRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 借阅记录展示对象：继承实体（表结构），
 * 再补上页面需要但表里没有的关联字段：
 * bookTitle 来自 book 表，username 来自 user 表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BorrowRecordVO extends BorrowRecord {

    // 关联 book 表：书名
    private String bookTitle;

    // 关联 user 表：用户名
    private String username;

}
