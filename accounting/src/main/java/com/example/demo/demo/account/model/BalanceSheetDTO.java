package com.example.demo.demo.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceSheetDTO {
    // 资产
    private List<AccountBalance> assets;
    // 负债
    private List<AccountBalance> liabilities;
    // 权益
    private List<AccountBalance> equity;

    // 合计
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal totalEquity;

    @Data
    @AllArgsConstructor
    public static class AccountBalance {
        private String code;
        private String name;
        private BigDecimal balance;
    }
}
