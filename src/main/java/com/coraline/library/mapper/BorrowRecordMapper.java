package com.coraline.library.mapper;

import com.coraline.library.entity.BorrowRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowRecordMapper {


    // 新增借阅记录
    int insert(BorrowRecord record);


    // 根据借阅记录id查询
    BorrowRecord findById(Long id);


    // 查询用户当前借阅数量
    int countBorrowingByUserId(Long userId);


    // 查询用户是否已经借过该书
    int countUserBook(Long userId, Long bookId);


    // 查询用户借阅记录
    List<BorrowRecord> findByUserId(Long userId);


    // 归还图书
    int returnBook(Long id);

}
