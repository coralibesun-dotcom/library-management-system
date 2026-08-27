package com.coraline.library.service.impl;

import com.coraline.library.common.PageResult;
import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.context.UserContext;
import com.coraline.library.common.enums.BookStatusEnum;
import com.coraline.library.common.enums.BorrowStatusEnum;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.BorrowDTO;
import com.coraline.library.dto.BorrowQueryDTO;
import com.coraline.library.entity.Book;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.BookMapper;
import com.coraline.library.mapper.BorrowRecordMapper;
import com.coraline.library.service.BorrowRecordService;
import com.coraline.library.vo.BorrowRecordVO;
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


        // 不再相信前端传来的userId
        Long userId = UserContext.getUserId();


        // 1. 查询图书是否存在
        Book book = bookMapper.findById(borrowDTO.getBookId());


        if(book == null){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_NOT_FOUND,
                    "图书不存在"
            );

        }


        // 2. 判断用户是否已经借过该书
        if(hasBorrowed(userId, borrowDTO.getBookId())){

            throw new BusinessException(
                    ResultCodeEnum.BORROW_ALREADY,
                    "该图书已经借阅，不能重复借阅"
            );

        }


        // 3. 判断用户当前借阅数量
        int count =
                borrowRecordMapper.countBorrowingByUserId(userId);


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


        // 6. 减少库存
        // 先扣库存，再新增借阅记录
        // 防止库存扣失败，但是借阅记录已经生成
        int result =
                bookMapper.decreaseStock(
                        borrowDTO.getBookId()
                );


        // 修改点：
        // 并发情况下，如果库存已经被别人借走
        // SQL会返回0，这里直接终止
        if(result == 0){

            throw new BusinessException(
                    ResultCodeEnum.STOCK_NOT_ENOUGH,
                    "库存不足"
            );

        }



        // 7. 新增借阅记录
        BorrowRecord record = new BorrowRecord();


        record.setUserId(userId);

        record.setBookId(
                borrowDTO.getBookId()
        );


        record.setStatus(
                BorrowStatusEnum.BORROWING.getCode()
        );


        borrowRecordMapper.insert(record);

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
    @Override
    public PageResult<BorrowRecordVO> findPage(BorrowQueryDTO dto) {

        // 1. 参数兜底（和图书分页同一套防御）
        if(dto == null){
            dto = new BorrowQueryDTO();
        }
        if(dto.getPageNum() == null){
            dto.setPageNum(1);
        }
        if(dto.getPageSize() == null){
            dto.setPageSize(10);
        }

        // 2. 权限收敛：只有管理员能看所有人；
        //    普通用户不管前端传没传 userId，一律强制改成自己（防越权查别人的记录）
        if(!"ADMIN".equals(UserContext.getRole())){
            dto.setUserId(UserContext.getUserId());
        }

        // 3. 页码换算成 SQL 的偏移量：第2页 = 跳过第1页的10条
        int offset = (dto.getPageNum() - 1) * dto.getPageSize();

        // 4. 两条 SQL：一条取当页数据（JOIN 出书名/用户名），一条数总数（分页器要显示"共 N 条"）
        List<BorrowRecordVO> records =
                borrowRecordMapper.findPage(dto, offset, dto.getPageSize());

        Long total =
                borrowRecordMapper.count(dto);

        return new PageResult<>(records, total);
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

        //2.校验归还人是否是借阅人
        Long currentUserId =
                UserContext.getUserId();


        if(currentUserId == null
                || !currentUserId.equals(record.getUserId())){

            throw new BusinessException(
                    ResultCodeEnum.FORBIDDEN,
                    "不能归还其他用户的借阅记录"
            );

        }

        // 3. 判断是否已经归还
        if(BorrowStatusEnum.RETURNED.getCode()
                .equals(record.getStatus())){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_RETURNED,
                    "该图书已经归还"
            );

        }


        // 4. 更新借阅状态
        int result =
                borrowRecordMapper.returnBook(id);

        if(result == 0){

            throw new BusinessException(
                    ResultCodeEnum.BOOK_RETURNED,
                    "该图书已经归还"
            );

        }
        // 5. 增加库存
        bookMapper.increaseStock(record.getBookId());

    }
}
