package io.github.ricewines.sys.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/// 上交所基金列表查询响应
@Data
public class SseFundQueryResponse {

    private List<String> actionErrors;
    private List<String> actionMessages;
    private Map<String, List<String>> fieldErrors;
    private String isPagination;
    private String jsonCallBack;
    private String locale;
    private SseFundPageHelp pageHelp;
    private String pageNo;
    private String pageSize;
    private String queryDate;
    private List<SseFund> result;
    private String securityCode;
    private String sqlId;
    private Object texts;
    private String type;
    private String validateCode;
}
