package com.coraline.library.mapper;

import com.coraline.library.dto.BorrowQueryDTO;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.vo.BorrowRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    int countUserBook(
            @Param("userId") Long userId,
            @Param("bookId") Long bookId
    );


    // 查询我的借阅记录
    List<BorrowRecord> findByUserId(Long userId);


    //查询全部借阅记录
    List<BorrowRecord> findAll();

    // 归还图书
    int returnBook(Long id);

    // 把借阅中超过30天未还的记录批量标记为逾期，返回影响行数
    int markOverdue();


    // 分页查询借阅记录（dto.userId 有值只查这个人的，没值查所有人）
    // 返回 VO：JOIN 出书名和用户名，页面直接展示
    List<BorrowRecordVO> findPage(
            @Param("dto") BorrowQueryDTO dto,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    // 分页配套：按同样条件数总数
    Long count(@Param("dto") BorrowQueryDTO dto);
}
