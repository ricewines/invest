package com.example.demo.demo.account.service.impl;

import com.example.demo.demo.account.model.BalanceSheetDTO;
import com.example.demo.demo.account.model.Account;
import com.example.demo.demo.account.model.VoucherEntry;
import com.example.demo.demo.account.repository.AccountRepository;
import com.example.demo.demo.account.repository.VoucherEntryRepository;
import com.example.demo.demo.account.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final VoucherEntryRepository voucherEntryRepository;

    @Override
    public BalanceSheetDTO getBalanceSheet(String startDate, String endDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        // 1. 查询所有科目
        List<Account> allAccounts = accountRepository.findAll();

        // 2. 按科目类型计算余额
        List<BalanceSheetDTO.AccountBalance> assets = calculateBalance(allAccounts, "资产", sDate, eDate);
        List<BalanceSheetDTO.AccountBalance> liabilities = calculateBalance(allAccounts, "负债", sDate, eDate);
        List<BalanceSheetDTO.AccountBalance> equity = calculateBalance(allAccounts, "权益", sDate, eDate);

        // 3. 合计
        BigDecimal totalAssets = sum(assets);
        BigDecimal totalLiabilities = sum(liabilities);
        BigDecimal totalEquity = sum(equity);

        // 4. 封装返回
        BalanceSheetDTO dto = new BalanceSheetDTO();
        dto.setAssets(assets);
        dto.setLiabilities(liabilities);
        dto.setEquity(equity);
        dto.setTotalAssets(totalAssets);
        dto.setTotalLiabilities(totalLiabilities);
        dto.setTotalEquity(totalEquity);

        return dto;
    }

    // 核心：科目余额 = 借方合计 - 贷方合计（IFRS 资产负债方向）
    private List<BalanceSheetDTO.AccountBalance> calculateBalance(
            List<Account> accounts,
            String type,
            LocalDate start,
            LocalDate end) {

        return accounts.stream()
                .filter(a -> type.equals(a.getType()))
                .map(account -> {
                    // 该科目在期间内的所有分录
                    List<VoucherEntry> entries = voucherEntryRepository
                            .findByAccountIdAndVoucher_VoucherDateBetween(account.getId(), start, end);

                    BigDecimal debitSum = entries.stream()
                            .map(VoucherEntry::getDebit)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal creditSum = entries.stream()
                            .map(VoucherEntry::getCredit)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal balance = debitSum.subtract(creditSum);

                    return new BalanceSheetDTO.AccountBalance(
                            account.getCode(),
                            account.getName(),
                            balance
                    );
                })
                .collect(Collectors.toList());
    }

    private BigDecimal sum(List<BalanceSheetDTO.AccountBalance> list) {
        return list.stream()
                .map(BalanceSheetDTO.AccountBalance::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
