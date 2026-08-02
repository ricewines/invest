package io.github.ricewines.invest.account.service.impl;

import io.github.ricewines.invest.account.model.Voucher;
import io.github.ricewines.invest.account.model.VoucherEntry;
import io.github.ricewines.invest.account.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    // 生成IFRS合规凭证
    @Transactional
    public Voucher createVoucher(Voucher voucher) {
        // 自动生成凭证号
        voucher.setVoucherNo("V-" + UUID.randomUUID().toString().substring(0,8));
        voucher.setVoucherDate(LocalDate.now());
        voucher.setStatus("DRAFT");
        voucher.setIfrsBasis("IFRS / IAS Compliance");

        // IFRS 强制要求：借贷平衡
        if (!isDebitCreditEqual(voucher)) {
            throw new RuntimeException("IFRS 违反：借贷必须相等！");
        }

        for (VoucherEntry entry : voucher.getEntries()) {
            entry.setVoucher(voucher);
        }

        return voucherRepository.save(voucher);
    }

    // 借贷平衡校验（所有会计系统核心）
    private boolean isDebitCreditEqual(Voucher voucher) {
        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        for (VoucherEntry entry : voucher.getEntries()) {
            totalDebit = totalDebit.add(entry.getDebit() == null ? BigDecimal.ZERO : entry.getDebit());
            totalCredit = totalCredit.add(entry.getCredit() == null ? BigDecimal.ZERO : entry.getCredit());
        }

        return totalDebit.compareTo(totalCredit) == 0;
    }
}
