package io.github.ricewines.sys.config;

import lombok.Data;

/**
 * 前端页面相关配置
 */
@Data
public class PageConfig {
    /** 应用域名，示例: <a href="http://127.0.0.1:6014">...</a> */
    private String domain = "http://localhost";
}
