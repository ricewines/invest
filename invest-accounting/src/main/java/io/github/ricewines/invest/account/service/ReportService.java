package io.github.ricewines.invest.account.service;

import io.github.ricewines.invest.account.model.BalanceSheetDTO;

public interface ReportService {
    BalanceSheetDTO getBalanceSheet(String startDate, String endDate);
}
