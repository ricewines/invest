package com.example.demo.demo.account.repository;

import com.example.demo.demo.account.model.VoucherEntry;
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
