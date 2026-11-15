package io.github.ricewines.sys.model;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * 短信记录VO
 * 返回给前端页面展示的数据模型
 */
@Data
public class SmsRecordVO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 发送号码
     */
    private String sender;
    /**
     * 接收号码
     */
    private String receiver;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 短信接收时间
     */
    private LocalDateTime receiveTime;
    /**
     * 是否已读 0未读 /1已读
     */
    private Integer isRead;
    /**
     * 入库创建时间
     */
    private LocalDateTime createTime;
}
