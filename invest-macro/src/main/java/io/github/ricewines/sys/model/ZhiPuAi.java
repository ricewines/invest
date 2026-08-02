package io.github.ricewines.sys.model;

import lombok.Data;

/// 智谱AI
@Data
public class ZhiPuAi {
    /// API KEY
    private String apiKey;
    /// 模型名
    private String model = "glm-4.7-flash";
}
