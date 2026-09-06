-- 邮箱订阅时态表：每次订阅状态变更都关闭旧版本并插入新版本。
CREATE TABLE IF NOT EXISTS email_subscription_temporal (
  biz_id BIGINT NOT NULL, -- 订阅业务唯一 ID
  version INT NOT NULL, -- 版本号，每次状态变更加 1
  email VARCHAR(128) NOT NULL, -- 订阅邮箱
  sub_type SMALLINT NOT NULL, -- 订阅类型：1=投资比例
  status SMALLINT NOT NULL, -- 订阅状态：1=生效，0=取消
  unsubscribe_token VARCHAR(64) NOT NULL, -- 取消订阅令牌
  valid_from TIMESTAMP(6) WITH TIME ZONE NOT NULL, -- 本版本生效时间，包含时区
  valid_to TIMESTAMP(6) WITH TIME ZONE NULL, -- 本版本失效时间；NULL 表示当前版本
  PRIMARY KEY (biz_id)
);

-- 按邮箱和订阅类型查询订阅记录。
CREATE INDEX IF NOT EXISTS idx_email_type ON email_subscription_temporal (email, sub_type);
-- 支持按时态有效区间查询记录。
CREATE INDEX IF NOT EXISTS idx_valid_range ON email_subscription_temporal (valid_from, valid_to);
-- 支持从邮件链接令牌查询当前订阅记录。
CREATE INDEX IF NOT EXISTS idx_unsubscribe_token ON email_subscription_temporal (unsubscribe_token);

COMMENT ON TABLE email_subscription_temporal IS '邮箱订阅时态表';
COMMENT ON COLUMN email_subscription_temporal.biz_id IS '订阅业务唯一 ID';
COMMENT ON COLUMN email_subscription_temporal.version IS '订阅状态版本号';
COMMENT ON COLUMN email_subscription_temporal.email IS '订阅邮箱';
COMMENT ON COLUMN email_subscription_temporal.sub_type IS '订阅类型：1=投资比例';
COMMENT ON COLUMN email_subscription_temporal.status IS '订阅状态：1=生效，0=取消';
COMMENT ON COLUMN email_subscription_temporal.unsubscribe_token IS '取消订阅令牌';
COMMENT ON COLUMN email_subscription_temporal.valid_from IS '本版本生效时间';
COMMENT ON COLUMN email_subscription_temporal.valid_to IS '本版本失效时间；NULL 表示当前版本';