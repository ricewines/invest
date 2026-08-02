package io.github.ricewines;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/// 宏观
@EnableScheduling
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
