package io.github.ricewines.invest.account.controller;

import io.github.ricewines.invest.account.model.BalanceSheetDTO;
import io.github.ricewines.invest.account.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("balance-sheet")
    public BalanceSheetDTO balanceSheet(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return reportService.getBalanceSheet(startDate, endDate);
    }
}
