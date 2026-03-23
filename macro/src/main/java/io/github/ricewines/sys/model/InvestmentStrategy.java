package io.github.ricewines.sys.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 投资策略数据模型
 */
@Data // 需引入lombok，或手动写get/set
public class InvestmentStrategy {
    // 资产配置比例（小数形式，如0.60）
    @JsonProperty("货币")
    private Double currencyRatio; // 货币
    @JsonProperty("债券")
    private Double bondRatio;     // 债券
    @JsonProperty("股票")
    private Double stockRatio;    // 股票
    @JsonProperty("商品")
    private Double commodityRatio;// 商品
}