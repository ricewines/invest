package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.constant.EmailSubscriptionTemporal;
import io.github.ricewines.sys.model.SubscribeDTO;
import io.github.ricewines.sys.service.EmailSubscribeTemporalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.jooq.impl.DSL.*;

@RestController
@RequestMapping("subscribe")
@RequiredArgsConstructor
public class SubscribeController {
    private final EmailSubscribeTemporalService subscribeService;


    @PostMapping("/add")
    public String addSubscribe(@Valid SubscribeDTO dto) {
        subscribeService.subscribe(dto.getEmail());
        return "订阅成功";
    }


    @PostMapping("unsubscribe/confirm")
    public String confirmUnsubscribe(@RequestParam String token) {
        return subscribeService.confirmUnSubscribe(token) ? "已成功取消订阅" : "token无效或已取消";
    }

    @GetMapping("un-subscribe-token-to-email")
    public String unSubscribeTokenToEmail(@RequestParam String token) {
        Record record = subscribeService.findCurrentByToken(token);
        return record.get(EmailSubscriptionTemporal.EMAIL);
    }
}