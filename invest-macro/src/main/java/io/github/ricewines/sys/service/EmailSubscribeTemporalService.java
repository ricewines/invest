package io.github.ricewines.sys.service;

import io.github.ricewines.sys.constant.EmailSubscriptionTemporal;
import io.github.ricewines.sys.model.SubscribeConstant;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static io.github.ricewines.sys.constant.EmailSubscriptionTemporal.*;

@Service
@RequiredArgsConstructor
public class EmailSubscribeTemporalService implements SubscribeConstant {
    private final DSLContext dsl;
    private final org.jooq.Table<Record> table = EmailSubscriptionTemporal.TABLE;
    private final org.jooq.Field<Long> bizId = BIZ_ID;
    private final org.jooq.Field<Integer> version = VERSION;
    private final org.jooq.Field<String> email = EMAIL;
    private final org.jooq.Field<Integer> subType = SUB_TYPE;
    private final org.jooq.Field<Integer> status = STATUS;
    private final org.jooq.Field<String> token = TOKEN;
    private final org.jooq.Field<OffsetDateTime> validFrom = VALID_FROM;
    private final org.jooq.Field<OffsetDateTime> validTo = VALID_TO;

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneId.of("Asia/Shanghai"));
    }

    private OffsetDateTime infinity() {
        return null;
    }


    public Record findCurrentByToken(String value) {
        return dsl.selectFrom(table).where(token.eq(value)).and(validTo.isNull()).fetchOne();
    }

    @Transactional(rollbackFor = Exception.class)
    public void subscribe(String address) {
        OffsetDateTime now = now();
        Record current = dsl.selectFrom(table).where(email.eq(address)).and(subType.eq(SUB_TYPE_INVEST_RATIO))
                .and(validTo.isNull()).forUpdate().fetchOne();
        long id = current == null ? Math.abs(UUID.randomUUID().getMostSignificantBits()) : current.get(bizId);
        int nextVersion = current == null ? 1 : current.get(version) + 1;
        String unsubscribeToken = current == null ? "sub_" + UUID.randomUUID().toString().replace("-", "") : current.get(token);
        if (current != null)
            dsl.update(table).set(validTo, now).where(bizId.eq(id)).and(version.eq(nextVersion - 1)).execute();
        dsl.insertInto(table).set(bizId, id).set(version, nextVersion).set(email, address).set(subType, SUB_TYPE_INVEST_RATIO)
                .set(status, STATUS_ACTIVE).set(token, unsubscribeToken).set(validFrom, now).set(validTo, infinity()).execute();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirmUnSubscribe(String value) {
        OffsetDateTime now = now();
        Record current = dsl.selectFrom(table).where(token.eq(value)).and(validTo.isNull()).forUpdate().fetchOne();
        if (current == null || current.get(status) == STATUS_CANCEL) return false;
        long id = current.get(bizId);
        int oldVersion = current.get(version);
        dsl.update(table).set(validTo, now).where(bizId.eq(id)).and(version.eq(oldVersion)).execute();
        dsl.insertInto(table).set(bizId, id).set(version, oldVersion + 1).set(email, current.get(email))
                .set(subType, SUB_TYPE_INVEST_RATIO).set(status, STATUS_CANCEL).set(token, value)
                .set(validFrom, now).set(validTo, infinity()).execute();
        return true;
    }
}