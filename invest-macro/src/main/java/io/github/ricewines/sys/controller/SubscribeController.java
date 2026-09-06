package io.github.ricewines.sys.controller;

import io.github.ricewines.sys.model.SubscribeDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/// 订阅投资
@HttpExchange("subscribe")
public interface SubscribeController {


    @PostExchange("add")
    String subscribe(@Valid SubscribeDTO dto);


    @PostExchange("unsubscribe/confirm")
    String confirmUnsubscribe(@RequestParam String token);

    @GetExchange("un-subscribe-token-to-email")
    String unSubscribeTokenToEmail(@RequestParam String token);
}