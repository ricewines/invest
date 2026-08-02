package io.github.ricewines.invest.account.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter  // 只留 getter
@Setter  // 只留 setter
public class Voucher {
    private Long id;

    private String voucherNo;
    private LocalDate voucherDate;
    private String description;
    private String status;

    private List<VoucherEntry> entries;

    private String ifrsBasis;
}