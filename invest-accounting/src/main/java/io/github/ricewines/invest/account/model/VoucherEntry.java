package io.github.ricewines.invest.account.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter  // 只留 getter
@Setter  // 只留 setter
public class VoucherEntry {
    private Long id;

    private Voucher voucher;

    private Account account;

    private BigDecimal debit;
    private BigDecimal credit;
    private String remark;
}
