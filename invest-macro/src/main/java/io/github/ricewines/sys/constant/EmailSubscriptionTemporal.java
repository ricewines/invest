package io.github.ricewines.sys.constant;

import org.jooq.Record;

import java.time.OffsetDateTime;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.unquotedName;

/// 邮箱订阅时态表
public class EmailSubscriptionTemporal {

    public static final org.jooq.Table<Record> TABLE = table("EMAIL_SUBSCRIPTION_TEMPORAL");
    public static final org.jooq.Field<Long> BIZ_ID = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "BIZ_ID"), Long.class);
    public static final org.jooq.Field<Integer> VERSION = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "VERSION"), Integer.class);
    public static final org.jooq.Field<String> EMAIL = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "EMAIL"), String.class);
    public static final org.jooq.Field<Integer> SUB_TYPE = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "SUB_TYPE"), Integer.class);
    public static final org.jooq.Field<Integer> STATUS = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "STATUS"), Integer.class);
    public static final org.jooq.Field<String> TOKEN = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "UNSUBSCRIBE_TOKEN"), String.class);
    public static final org.jooq.Field<OffsetDateTime> VALID_FROM = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "VALID_FROM"), OffsetDateTime.class);
    public static final org.jooq.Field<OffsetDateTime> VALID_TO = field(name("EMAIL_SUBSCRIPTION_TEMPORAL", "VALID_TO"), OffsetDateTime.class);
}
