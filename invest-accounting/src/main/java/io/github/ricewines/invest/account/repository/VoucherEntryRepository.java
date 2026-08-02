package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.VoucherEntry;
import io.github.ricewines.invest.account.model.Account;
import io.github.ricewines.invest.account.model.Voucher;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class VoucherEntryRepository {
    private final DSLContext dsl;

    public List<VoucherEntry> findByAccountIdAndVoucher_VoucherDateBetween(
            Long accountId, LocalDate startDate, LocalDate endDate) {
        Field<Long> veId = field(name("VE", "ID"), Long.class);
        Field<java.math.BigDecimal> veDebit = field(name("VE", "DEBIT"), java.math.BigDecimal.class);
        Field<java.math.BigDecimal> veCredit = field(name("VE", "CREDIT"), java.math.BigDecimal.class);
        Field<String> veRemark = field(name("VE", "REMARK"), String.class);

        Field<Long> aId = field(name("A", "ID"), Long.class);
        Field<String> aCode = field(name("A", "CODE"), String.class);
        Field<String> aName = field(name("A", "NAME"), String.class);
        Field<String> aType = field(name("A", "TYPE"), String.class);
        Field<String> aIfrsStandard = field(name("A", "IFRS_STANDARD"), String.class);

        Field<Long> vId = field(name("V", "ID"), Long.class);
        Field<String> vVoucherNo = field(name("V", "VOUCHER_NO"), String.class);
        Field<Date> vVoucherDate = field(name("V", "VOUCHER_DATE"), Date.class);
        Field<String> vDescription = field(name("V", "DESCRIPTION"), String.class);
        Field<String> vStatus = field(name("V", "STATUS"), String.class);
        Field<String> vIfrsBasis = field(name("V", "IFRS_BASIS"), String.class);

        return dsl.select(
                    veId,
                    veDebit,
                    veCredit,
                    veRemark,
                    aId,
                    aCode,
                    aName,
                    aType,
                    aIfrsStandard,
                    vId,
                    vVoucherNo,
                    vVoucherDate,
                    vDescription,
                    vStatus,
                    vIfrsBasis
                )
                .from(table("voucher_entries").as("VE"))
                .join(table("vouchers").as("V"))
                .on(field(name("V", "ID"), Long.class).eq(field(name("VE", "VOUCHER_ID"), Long.class)))
                .join(table("accounts").as("A"))
                .on(field(name("A", "ID"), Long.class).eq(field(name("VE", "ACCOUNT_ID"), Long.class)))
                .where(field(name("VE", "ACCOUNT_ID"), Long.class).eq(accountId))
                .and(field(name("V", "VOUCHER_DATE"), Date.class).between(Date.valueOf(startDate), Date.valueOf(endDate)))
                .fetch(record -> {
                    VoucherEntry entry = new VoucherEntry();
                    entry.setId(record.get(veId));
                    entry.setDebit(record.get(veDebit));
                    entry.setCredit(record.get(veCredit));
                    entry.setRemark(record.get(veRemark));

                    Account account = new Account();
                    account.setId(record.get(aId));
                    account.setCode(record.get(aCode));
                    account.setName(record.get(aName));
                    account.setType(record.get(aType));
                    account.setIfrsStandard(record.get(aIfrsStandard));
                    entry.setAccount(account);

                    Voucher voucher = new Voucher();
                    voucher.setId(record.get(vId));
                    voucher.setVoucherNo(record.get(vVoucherNo));
                    voucher.setVoucherDate(record.get(vVoucherDate).toLocalDate());
                    voucher.setDescription(record.get(vDescription));
                    voucher.setStatus(record.get(vStatus));
                    voucher.setIfrsBasis(record.get(vIfrsBasis));
                    entry.setVoucher(voucher);
                    return entry;
                });
    }
}
