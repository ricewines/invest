package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.model.MarketHoliday;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

/// 上交所休市安排
@HttpExchange("sse-closed-schedule")
public interface SseClosedScheduleController {

    /**
     * 获取上交所休市安排的 JSON 数据
     */
    @GetExchange("fetch")
    List<MarketHoliday> fetchClosedSchedule();
}
