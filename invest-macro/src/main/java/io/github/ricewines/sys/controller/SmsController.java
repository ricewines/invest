package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.model.PageResult;
import io.github.ricewines.sys.model.SmsForwardDTO;
import io.github.ricewines.sys.model.SmsRecordVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@HttpExchange("/sys/sms")
public interface SmsController {

    @PostExchange("/forward")
    void forward(@RequestBody SmsForwardDTO dto);

    @GetExchange("/subscribe")
    SseEmitter subscribe(@RequestParam String clientId);

    @GetExchange("/list")
    PageResult<SmsRecordVO> list(SmsForwardDTO.SmsQueryDTO dto);

    @PostExchange("/read")
    void read(@RequestParam Long id);

    @GetExchange("/unread-count")
    int unreadCount();
}
