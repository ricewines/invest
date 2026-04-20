package io.github.ricewines.sys.controller;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/// 投资比例
@HttpExchange("invest-ratio")
public interface InvestRatioController {

    /**
     * 加仓和调仓
     */
    @GetExchange("position-add-and-adjust")
    void positionAddAndAdjust();
}
