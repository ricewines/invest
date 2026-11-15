package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.model.PageResult;
import io.github.ricewines.sys.model.SmsForwardDTO;
import io.github.ricewines.sys.model.SmsPushDTO;
import io.github.ricewines.sys.model.SmsRecordVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 短信相关接口
 * 包含：设备短信推送回调、旧版短信转发、SSE实时订阅、短信列表查询、已读标记、未读统计
 */
@HttpExchange("/sys/sms")
public interface SmsController {

    /**
     * 【新】短信设备推送回调接口
     * POST /sys/sms/handle-push
     * 由短信硬件设备主动推送JSON：sender、message、timestamp
     * 成功返回200~299，异常则推送方会自动重试（最多3次，间隔3s）
     *
     * @param dto 设备推送报文
     */
    @PostExchange("/handle-push")
    void handlePush(@RequestBody SmsPushDTO dto);

    /**
     * 【旧】短信转发接口
     * POST /sys/sms/forward
     * 老的转发通道，带 sender/receiver/content/receiveTime 完整字段
     *
     * @param dto 转发短信DTO
     */
    @PostExchange("/forward")
    void forward(@RequestBody SmsForwardDTO dto);

    /**
     * SSE 实时短信订阅
     * GET /sys/sms/subscribe
     * 前端建立长连接，有新短信时主动推送SmsRecordVO
     *
     * @param clientId 客户端唯一标识，用于区分不同订阅端
     * @return SseEmitter SSE长连接发射器
     */
    @GetExchange("/subscribe")
    SseEmitter subscribe(@RequestParam String clientId);

    /**
     * 短信分页列表查询
     * GET /sys/sms/list
     * 支持按发送号码筛选、分页
     *
     * @param dto 查询条件（sender、pageNum、pageSize）
     * @return 分页结果
     */
    @GetExchange("/list")
    PageResult<SmsRecordVO> list(SmsForwardDTO.SmsQueryDTO dto);

    /**
     * 标记短信为已读
     * POST /sys/sms/read
     *
     * @param id 短信记录主键ID
     */
    @PostExchange("/read")
    void read(@RequestParam Long id);

    /**
     * 查询未读短信总数
     * GET /sys/sms/unread-count
     *
     * @return 未读条数
     */
    @GetExchange("/unread-count")
    int unreadCount();
}
