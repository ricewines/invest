package io.github.ricewines.sys.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/// 市场假日
public class MarketHoliday {
    /// 日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /// 是否休市
    private boolean closed;
}
