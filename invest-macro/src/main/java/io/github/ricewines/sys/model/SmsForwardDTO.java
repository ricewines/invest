package io.github.ricewines.sys.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsForwardDTO {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime receiveTime;

    @Data
    public static class SmsQueryDTO {
        private String sender;
        private Integer pageNum = 1;
        private Integer pageSize = 20;
    }
}



