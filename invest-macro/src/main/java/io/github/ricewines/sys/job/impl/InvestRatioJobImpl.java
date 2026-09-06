package io.github.ricewines.sys.job.impl;

import io.github.ricewines.sys.service.InvestRatioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/// 投资比例任务
@AllArgsConstructor
@Service
@Slf4j
public class InvestRatioJobImpl {

    /// 投资比例
    private InvestRatioService investRatioService;

    /**
     * 加仓和调仓
     */
    @Scheduled(cron = "0 20 8 * * *", zone = "Asia/Shanghai")
    public void positionAddAndAdjust() {
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        if (dayOfMonth != 5) {
            log.info("今天不是每月5号，不执行投资比例邮件发送任务");
        } else {
            investRatioService.positionAddAndAdjust();
        }
    }
}
