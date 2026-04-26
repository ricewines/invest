package com.example.demo.demo.account.controller;

import com.example.demo.demo.account.model.BalanceSheetDTO;
import com.example.demo.demo.account.service.ReportService;
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
