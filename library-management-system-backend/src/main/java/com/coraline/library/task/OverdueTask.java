package com.coraline.library.task;

import com.coraline.library.mapper.BorrowRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 逾期标记定时任务
 *
 * 每天凌晨 1 点跑一次，把"借阅中且借出超过 30 天"的记录批量改成"逾期"。
 * 没有这个任务之前，状态 2 只存在于数据库注释里，永远不会出现。
 */
@Component
public class OverdueTask {

    private static final Logger log = LoggerFactory.getLogger(OverdueTask.class);

    private final BorrowRecordMapper borrowRecordMapper;

    public OverdueTask(BorrowRecordMapper borrowRecordMapper) {
        this.borrowRecordMapper = borrowRecordMapper;
    }

    // cron 六位：秒 分 时 日 月 周 → 每天 01:00:00 执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void markOverdue() {

        int count = borrowRecordMapper.markOverdue();

        // 有没有扫到逾期的都打一条日志，方便以后排查"为什么没标上"
        log.info("逾期标记任务完成，本次标记 {} 条借阅记录为逾期", count);
    }
}
