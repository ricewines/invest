package io.github.ricewines.sys.service.impl;

import io.github.ricewines.sys.controller.SmsController;
import io.github.ricewines.sys.model.PageResult;
import io.github.ricewines.sys.model.SmsForwardDTO;
import io.github.ricewines.sys.model.SmsPushDTO;
import io.github.ricewines.sys.model.SmsRecordVO;
import io.github.ricewines.sys.po.SmsRecord;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@RestController
public class SmsServiceImpl implements SmsController {

    private DSLContext dsl;

    private SsePushManager ssePushManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handlePush(SmsPushDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receiveTime;
        if (dto.getTimestamp() != null) {
            receiveTime = dto.getTimestamp();
        } else {
            receiveTime = now;
        }

        SmsRecord smsRecord = new SmsRecord();
        // sender：原始推送字段 SIM-+8613800138000 直接存入sender
        smsRecord.setSender(dto.getSender());
        // 【注意】推送报文里没有 receiver 字段，这里根据你的业务选择：
        // 方案A：留空（推荐，如果设备推送不带本机号码）
        smsRecord.setReceiver(null);
        // 方案B：如果你需要从sender里提取本机SIM号码，可以在这里解析，我后面给解析示例
        smsRecord.setContent(dto.getMessage());
        smsRecord.setReceiveTime(receiveTime);
        smsRecord.setIsRead(0);
        smsRecord.setCreateTime(now);

        dsl.insertInto(SmsRecord.TABLE)
                .set(SmsRecord.SENDER, smsRecord.getSender())
                .set(SmsRecord.RECEIVER, smsRecord.getReceiver())
                .set(SmsRecord.CONTENT, smsRecord.getContent())
                .set(SmsRecord.RECEIVE_TIME, smsRecord.getReceiveTime())
                .set(SmsRecord.IS_READ, smsRecord.getIsRead())
                .set(SmsRecord.CREATE_TIME, smsRecord.getCreateTime())
                .execute();

        // 转VO，推送SSE，和你原有forward逻辑保持一致
        SmsRecordVO vo = toVO(smsRecord);
        ssePushManager.pushNewSms(vo);
    }

    @Override
    @Transactional
    public void forward(SmsForwardDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receiveTime = dto.getReceiveTime() == null ? now : dto.getReceiveTime();
        SmsRecord smsRecord = new SmsRecord();

        smsRecord.setSender(dto.getSender());
        smsRecord.setReceiver(dto.getReceiver());
        smsRecord.setContent(dto.getContent());
        smsRecord.setReceiveTime(receiveTime);
        smsRecord.setIsRead(0);
        smsRecord.setCreateTime(now);

        dsl.insertInto(SmsRecord.TABLE)
                .set(SmsRecord.SENDER, smsRecord.getSender())
                .set(SmsRecord.RECEIVER, smsRecord.getReceiver())
                .set(SmsRecord.CONTENT, smsRecord.getContent())
                .set(SmsRecord.RECEIVE_TIME, smsRecord.getReceiveTime())
                .set(SmsRecord.IS_READ, smsRecord.getIsRead())
                .set(SmsRecord.CREATE_TIME, smsRecord.getCreateTime())
                .execute();
        SmsRecordVO vo = toVO(smsRecord);
        ssePushManager.pushNewSms(vo);
    }

    @Override
    public SseEmitter subscribe(String clientId) {
        return ssePushManager.connect(clientId);
    }


    @Override
    public PageResult<SmsRecordVO> list(SmsForwardDTO.SmsQueryDTO dto) {
        SelectConditionStep<Record> step = dsl.selectFrom(SmsRecord.TABLE).where(DSL.trueCondition());
        if (dto.getSender() != null && !dto.getSender().isBlank()) {
            step = step.and(SmsRecord.SENDER.eq(dto.getSender()));
        }
        int total = dsl.fetchCount(step);
        List<SmsRecord> smsRecords = step.orderBy(SmsRecord.RECEIVE_TIME.desc())
                .limit((dto.getPageNum() - 1) * dto.getPageSize(), dto.getPageSize())
                .fetchInto(SmsRecord.class);
        List<SmsRecordVO> list = smsRecords.stream().map(this::toVO).toList();
        return new PageResult<>(list, total);
    }

    @Transactional
    @Override
    public void read(Long id) {
        dsl.update(SmsRecord.TABLE)
                .set(SmsRecord.IS_READ, 1)
                .where(SmsRecord.ID.eq(id))
                .execute();
    }

    @Override
    public int unreadCount() {
        return dsl.fetchCount(dsl.selectFrom(SmsRecord.TABLE).where(SmsRecord.IS_READ.eq(0)));
    }

    private SmsRecordVO toVO(SmsRecord r) {
        SmsRecordVO vo = new SmsRecordVO();
        vo.setId(r.getId());
        vo.setSender(r.getSender());
        vo.setReceiver(r.getReceiver());
        vo.setContent(r.getContent());
        vo.setReceiveTime(r.getReceiveTime());
        vo.setIsRead(r.getIsRead());
        vo.setCreateTime(r.getCreateTime());
        return vo;
    }

}
