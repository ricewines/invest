package io.github.ricewines.sys.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 投资比例邮件模板数据模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestRatioMailModel {
    /** 推送时间 */
    private String bizTime;
    /** 货币类资产比例 */
    private String currencyRatio;
    /** 债券类资产比例 */
    private String bondRatio;
    /** 股票类资产比例 */
    private String stockRatio;
    /** 商品类资产比例 */
    private String commodityRatio;
    /** 取消订阅地址 */
    private String unsubscribeUrl;
}
