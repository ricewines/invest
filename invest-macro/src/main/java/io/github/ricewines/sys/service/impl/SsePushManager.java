package io.github.ricewines.sys.service.impl;

import io.github.ricewines.sys.model.SmsRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SsePushManager {
    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public SseEmitter connect(String clientId) {
        SseEmitter emitter = new SseEmitter(0L);
        emitterMap.put(clientId, emitter);
        emitter.onCompletion(() -> emitterMap.remove(clientId));
        emitter.onTimeout(() -> emitterMap.remove(clientId));
        emitter.onError((e) -> emitterMap.remove(clientId));
        return emitter;
    }

    public void pushNewSms(SmsRecordVO record) {

        log.info("Pushing new SMS to {} clients", emitterMap.size());

        for (SseEmitter emitter : emitterMap.values()) {
            try {
                emitter.send(SseEmitter.event()
                        .name("new-sms")
                        .data(new ObjectMapper().writeValueAsString(record)));
            } catch (Exception e) {
                log.error("Error occurred while pushing SMS to client", e);
                emitter.complete();
            }
        }
    }
}
