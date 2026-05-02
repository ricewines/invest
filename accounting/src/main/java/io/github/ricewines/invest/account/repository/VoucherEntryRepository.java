package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.VoucherEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface VoucherEntryRepository extends JpaRepository<VoucherEntry, Long> {

    // 错误：findByAccountIdAndVoucherDateBetween
    // 正确：findByAccountIdAndVoucher_VoucherDateBetween
    List<VoucherEntry> findByAccountIdAndVoucher_VoucherDateBetween(
            Long accountId,
            LocalDate startDate,
            LocalDate endDate);
}
