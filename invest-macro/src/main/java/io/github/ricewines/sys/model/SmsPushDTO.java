package io.github.ricewines.sys.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsPushDTO {
    /**
     * SIM卡名称 + 发送号码，示例：SIM-+8613800138000
     */
    private String sender;
    /**
     * 短信内容
     */
    private String message;
    /**
     * 时间字符串 YYYY-MM-DD HH:MM:SS
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
