package io.github.ricewines.sys.service;

import io.github.ricewines.sys.model.MarketHoliday;

import java.util.List;

/// 上交所休市安排服务
public interface SseClosedScheduleService {
    /**
     * 获取上交所休市安排 JSON 数组
     */
    List<MarketHoliday> fetchClosedSchedule();
}
