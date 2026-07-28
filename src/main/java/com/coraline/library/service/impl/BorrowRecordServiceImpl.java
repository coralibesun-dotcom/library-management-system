package com.coraline.library.service.impl;

import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.enums.BookStatusEnum;
import com.coraline.library.common.enums.BorrowStatusEnum;
import com.coraline.library.entity.Book;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.mapper.BookMapper;
import com.coraline.library.mapper.BorrowRecordMapper;
import com.coraline.library.service.BorrowRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService  {

    private final BorrowRecordMapper borrowRecordMapper;
    private final BookMapper bookMapper;
    public BorrowRecordServiceImpl(
            BorrowRecordMapper borrowRecordMapper,
            BookMapper bookMapper
    ){
        this.borrowRecordMapper = borrowRecordMapper;
        this.bookMapper = bookMapper;
    }

    @Log("借阅图书")
    @Override
    @Transactional
    public void borrowBook(Long userId, Long bookId) {


        // 1. 查询图书是否存在
        Book book = bookMapper.findById(bookId);


        if(book == null){
            throw new RuntimeException("图书不存在");
        }


        // 2. 判断用户是否已经借过该书
        if(hasBorrowed(userId, bookId)){

            throw new RuntimeException("该图书已经借阅，不能重复借阅");

        }


        // 3. 判断用户当前借阅数量
        int count =
                borrowRecordMapper.countBorrowingByUserId(userId);


        if(count >= 5){

            throw new RuntimeException("最多借5本书");

        }


        // 4. 判断图书状态
        if(!BookStatusEnum.ON_SALE.getCode()
                .equals(book.getStatus())){

            throw new RuntimeException("该图书不可借阅");

        }


        // 5. 判断库存
        if(book.getStock() <= 0){

            throw new RuntimeException("库存不足");

        }


        // 6. 新增借阅记录
        BorrowRecord record = new BorrowRecord();

        record.setUserId(userId);
        record.setBookId(bookId);
        record.setStatus(
                BorrowStatusEnum.BORROWING.getCode()
        );


        borrowRecordMapper.insert(record);


        // 7. 减少库存
        bookMapper.decreaseStock(bookId);

    }
    @Override
    public int countBorrowingByUserId(Long userId) {
        return borrowRecordMapper.countBorrowingByUserId(userId);
    }

    @Override
    public boolean hasBorrowed(Long userId, Long bookId) {

        return borrowRecordMapper.countUserBook(userId, bookId) > 0;

    }

    @Override
    public List<BorrowRecord> findByUserId(Long userId) {
        return borrowRecordMapper.findByUserId(userId);
    }

    @Log("归还图书")
    @Override
    @Transactional
    public void returnBook(Long id) {


        // 1. 查询借阅记录
        BorrowRecord record = borrowRecordMapper.findById(id);


        if(record == null){

            throw new RuntimeException("借阅记录不存在");

        }


        // 2. 判断是否已经归还
        if(BorrowStatusEnum.RETURNED.getCode()
                .equals(record.getStatus())){

            throw new RuntimeException("该图书已经归还");

        }


        // 3. 更新借阅状态
        borrowRecordMapper.returnBook(id);


        // 4. 增加库存
        bookMapper.increaseStock(record.getBookId());

    }
}
