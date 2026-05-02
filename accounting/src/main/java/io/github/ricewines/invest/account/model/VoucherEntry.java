package io.github.ricewines.invest.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter  // 只留 getter
@Setter  // 只留 setter
@Table(name = "voucher_entries")
public class VoucherEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal debit;
    private BigDecimal credit;
    private String remark;
}
