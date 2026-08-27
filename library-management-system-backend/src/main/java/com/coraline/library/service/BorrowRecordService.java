package com.coraline.library.service;

import com.coraline.library.common.PageResult;
import com.coraline.library.dto.BorrowDTO;
import com.coraline.library.dto.BorrowQueryDTO;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.vo.BorrowRecordVO;

import java.util.List;

public interface BorrowRecordService {


    /**
     * 借阅图书
     */
    void borrowBook(BorrowDTO borrowDTO);


    /**
     * 查询用户当前借阅数量
     */
    int countBorrowingByUserId(Long userId);


    /**
     * 查询用户是否已经借过该书
     */
    boolean hasBorrowed(Long userId, Long bookId);


    /**
     * 查询我的借阅记录
     */
    List<BorrowRecord> findMyBorrow();


    /**
     * 查询全部借阅记录（管理员）
     */
    List<BorrowRecord> findAll();


    /**
     * 分页查询借阅记录：管理员看全部，普通用户只看自己的
     * 返回 VO（带书名/用户名），页面直接展示
     */
    PageResult<BorrowRecordVO> findPage(BorrowQueryDTO dto);


    /**
     * 归还图书
     */
    void returnBook(Long id);

}
