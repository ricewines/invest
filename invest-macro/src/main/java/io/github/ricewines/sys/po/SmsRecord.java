package io.github.ricewines.sys.po;

import lombok.Data;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;

/// 短信记录表
@Data
public class SmsRecord {

    public static final Table<Record> TABLE = DSL.table(DSL.name("SMS_RECORD"));

    public static final Field<Long> ID = DSL.field(DSL.name("ID"), Long.class);
    public static final Field<String> SENDER = DSL.field(DSL.name("SENDER"), String.class);
    public static final Field<String> RECEIVER = DSL.field(DSL.name("RECEIVER"), String.class);
    public static final Field<String> CONTENT = DSL.field(DSL.name("CONTENT"), String.class);
    public static final Field<LocalDateTime> RECEIVE_TIME = DSL.field(DSL.name("RECEIVE_TIME"), LocalDateTime.class);
    public static final Field<Integer> IS_READ = DSL.field(DSL.name("IS_READ"), Integer.class);
    public static final Field<LocalDateTime> CREATE_TIME = DSL.field(DSL.name("CREATE_TIME"), LocalDateTime.class);

    private Long id;
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime receiveTime;
    private Integer isRead;
    private LocalDateTime createTime;
}
