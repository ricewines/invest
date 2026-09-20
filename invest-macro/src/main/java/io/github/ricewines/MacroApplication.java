package io.github.ricewines;

import io.github.ricewines.sys.controller.SseFundController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.service.registry.ImportHttpServices;

/// 宏观
///
/// ✅ 增加 group="sse"，这个接口归入sse分组，绑定 spring.http.serviceclient.sse
@EnableScheduling
@ImportHttpServices(group = "sse", basePackageClasses = {SseFundController.class})
@SpringBootApplication
public class MacroApplication {
    /**
     * 主方法
     *
     * @param args 命令行参数
     */
    static void main(String[] args) {
        SpringApplication.run(MacroApplication.class, args);
    }
}
