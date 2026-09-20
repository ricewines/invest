package io.github.ricewines.sys.model;

import lombok.Data;

import java.util.List;

/// 上交所基金列表分页信息
@Data
public class SseFundPageHelp {

    private int beginPage;
    private int cacheSize;
    private List<SseFund> data;
    private String endDate;
    private Integer endPage;
    private Object objectResult;
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private int pageSizeWithOutLimit;
    private String searchDate;
    private Object sort;
    private String startDate;
    private int total;
}
