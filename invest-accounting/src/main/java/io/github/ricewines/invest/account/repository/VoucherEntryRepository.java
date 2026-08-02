package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.VoucherEntry;
import io.github.ricewines.invest.account.model.Account;
import io.github.ricewines.invest.account.model.Voucher;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class VoucherEntryRepository {
    private final DSLContext dsl;

    public List<VoucherEntry> findByAccountIdAndVoucher_VoucherDateBetween(
            Long accountId, LocalDate startDate, LocalDate endDate) {
        return dsl.select()
                .from(table("voucher_entries").as("ve"))
                .join(table("vouchers").as("v"))
                .on(field("v.id").eq(field("ve.voucher_id")))
                .join(table("accounts").as("a"))
                .on(field("a.id").eq(field("ve.account_id")))
                .where(field("ve.account_id").eq(accountId))
                .and(field("v.voucher_date").between(startDate, endDate))
                .fetch(record -> {
                    VoucherEntry entry = new VoucherEntry();
                    entry.setId(record.get(field("ve.id", Long.class)));
                    entry.setDebit(record.get(field("ve.debit", java.math.BigDecimal.class)));
                    entry.setCredit(record.get(field("ve.credit", java.math.BigDecimal.class)));
                    entry.setRemark(record.get(field("ve.remark", String.class)));

                    Account account = new Account();
                    account.setId(record.get(field("a.id", Long.class)));
                    account.setCode(record.get(field("a.code", String.class)));
                    account.setName(record.get(field("a.name", String.class)));
                    account.setType(record.get(field("a.type", String.class)));
                    account.setIfrsStandard(record.get(field("a.ifrs_standard", String.class)));
                    entry.setAccount(account);

                    Voucher voucher = new Voucher();
                    voucher.setId(record.get(field("v.id", Long.class)));
                    voucher.setVoucherNo(record.get(field("v.voucher_no", String.class)));
                    voucher.setVoucherDate(record.get(field("v.voucher_date", LocalDate.class)));
                    voucher.setDescription(record.get(field("v.description", String.class)));
                    voucher.setStatus(record.get(field("v.status", String.class)));
                    voucher.setIfrsBasis(record.get(field("v.ifrs_basis", String.class)));
                    entry.setVoucher(voucher);
                    return entry;
                });
    }
}
