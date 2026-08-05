package com.coraline.library.service.impl;

import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.context.UserContext;
import com.coraline.library.common.enums.BookStatusEnum;
import com.coraline.library.common.enums.BorrowStatusEnum;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.BorrowDTO;
import com.coraline.library.entity.Book;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.exception.BusinessException;
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
    public void borrowBook(BorrowDTO borrowDTO) {


        // 1. 查询图书是否存在
        Book book = bookMapper.findById(borrowDTO.getBookId());


        if(book == null){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_NOT_FOUND,
                    "图书不存在"
            );

        }


        // 2. 判断用户是否已经借过该书
        if(hasBorrowed(borrowDTO.getUserId(), borrowDTO.getBookId())){

            throw new BusinessException(
                    ResultCodeEnum.BORROW_ALREADY,
                    "该图书已经借阅，不能重复借阅"
            );

        }


        // 3. 判断用户当前借阅数量
        int count =
                borrowRecordMapper.countBorrowingByUserId(borrowDTO.getUserId());


        if(count >= 5){

            throw new BusinessException(
                    ResultCodeEnum.BORROW_LIMIT,
                    "最多借5本书"
            );

        }


        // 4. 判断图书状态
        if(!BookStatusEnum.ON_SALE.getCode()
                .equals(book.getStatus())){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_STATUS_ERROR,
                    "该图书不可借阅"
            );

        }


        // 5. 判断库存
        if(book.getStock() <= 0){

            throw new BusinessException(
                    ResultCodeEnum.STOCK_NOT_ENOUGH,
                    "库存不足"
            );

        }


        // 6. 新增借阅记录
        BorrowRecord record = new BorrowRecord();

        record.setUserId(borrowDTO.getUserId());
        record.setBookId(borrowDTO.getBookId());
        record.setStatus(
                BorrowStatusEnum.BORROWING.getCode()
        );


        borrowRecordMapper.insert(record);


        // 7. 减少库存
        bookMapper.decreaseStock(borrowDTO.getBookId());

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
    public List<BorrowRecord> findMyBorrow() {


        Long userId =
                UserContext.getUserId();


        return borrowRecordMapper.findByUserId(userId);

    }

    @Override
    public List<BorrowRecord> findAll() {

        return borrowRecordMapper.findAll();

    }
    @Log("归还图书")
    @Override
    @Transactional
    public void returnBook(Long id) {


        // 1. 查询借阅记录
        BorrowRecord record = borrowRecordMapper.findById(id);


        if(record == null){

            throw new BusinessException(
                    ResultCodeEnum.BORROW_RECORD_NOT_FOUND,
                    "借阅记录不存在"
            );

        }


        // 2. 判断是否已经归还
        if(BorrowStatusEnum.RETURNED.getCode()
                .equals(record.getStatus())){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_RETURNED,
                    "该图书已经归还"
            );

        }


        // 3. 更新借阅状态
        borrowRecordMapper.returnBook(id);


        // 4. 增加库存
        bookMapper.increaseStock(record.getBookId());

    }
}
