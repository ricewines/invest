package com.example.demo.demo.account.service;

import com.example.demo.demo.account.model.BalanceSheetDTO;

public interface ReportService {
    BalanceSheetDTO getBalanceSheet(String startDate, String endDate);
}
