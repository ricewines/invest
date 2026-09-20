package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.model.SseFundQueryResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/// 上交所基金列表查询
@HttpExchange(
        url = "commonSoaQuery.do",
        headers = {"Referer=https://www.sse.com.cn/"}
)
public interface SseFundController {

    /**
     * 查询上交所基金列表。
     *
     * @param isPagination 是否分页
     * @param pageSize     每页数量
     * @param pageNo       页码
     * @param sqlId        查询标识，固定为 FUND_LIST
     * @param fundType     基金类型
     * @param subClass     基金子类别，多个值以逗号分隔
     */
    @GetExchange
    SseFundQueryResponse listFunds(
            @RequestParam("isPagination") boolean isPagination,
            @RequestParam("pageHelp.pageSize") int pageSize,
            @RequestParam("pageHelp.pageNo") int pageNo,
            @RequestParam("sqlId") String sqlId,
            @RequestParam("fundType") String fundType,
            @RequestParam("subClass") String subClass
    );
}
