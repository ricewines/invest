package io.github.ricewines.sys.config;

import io.github.ricewines.sys.model.MailConfig;
import io.github.ricewines.sys.model.ZhiPuAi;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/// 投资配置
@Data
@AutoConfiguration
@ConfigurationProperties("invest")
public class InvestConfig {
    /// 智谱AI
    @NestedConfigurationProperty
    private ZhiPuAi zhiPuAi = new ZhiPuAi();
    /// 邮箱配置
    @NestedConfigurationProperty
    private MailConfig mailConfig = new MailConfig();

    // 页面相关配置（域名、上下文路径等）
    @NestedConfigurationProperty
    private PageConfig pageConfig = new PageConfig();

}
