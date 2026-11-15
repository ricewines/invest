-- 短信记录表：接收外部转发接口推送的短信，持久化存储，供前端查看历史短信、实时提醒
CREATE TABLE IF NOT EXISTS SMS_RECORD (
                                          ID BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                                          SENDER VARCHAR(32) NOT NULL COMMENT '短信发送号码（106/手机号等）',
                                          RECEIVER VARCHAR(32) NOT NULL COMMENT '短信接收号码',
                                          CONTENT TEXT NOT NULL COMMENT '短信正文内容',
                                          RECEIVE_TIME TIMESTAMP NOT NULL COMMENT '短信原始接收时间',
                                          IS_READ INT DEFAULT 0 COMMENT '是否已读：0-未读，1-已读',
                                          CREATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记录入库时间'
);

-- 发送号码索引：支持按发送号码快速筛选短信列表
CREATE INDEX IF NOT EXISTS IDX_SENDER ON SMS_RECORD(SENDER);
-- 接收时间索引：列表默认按短信接收时间倒序查询
CREATE INDEX IF NOT EXISTS IDX_RECEIVE_TIME ON SMS_RECORD(RECEIVE_TIME DESC);
