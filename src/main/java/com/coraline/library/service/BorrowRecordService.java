package com.coraline.library.service;

import com.coraline.library.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordService {


    /**
     * 借阅图书
     */
    void borrowBook(Long userId, Long bookId);


    /**
     * 查询用户当前借阅数量
     */
    int countBorrowingByUserId(Long userId);


    /**
     * 查询用户是否已经借过该书
     */
    boolean hasBorrowed(Long userId, Long bookId);


    /**
     * 查询用户借阅记录
     */
    List<BorrowRecord> findByUserId(Long userId);


    /**
     * 归还图书
     */
    void returnBook(Long id);

}
