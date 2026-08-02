package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.Voucher;
import io.github.ricewines.invest.account.model.VoucherEntry;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class VoucherRepository {
	private static final String VOUCHERS = "vouchers";
	private static final String ENTRIES = "voucher_entries";
	private static final String ID = "id";
	private static final String VOUCHER_ID = "voucher_id";

	private final DSLContext dsl;

	public Voucher save(Voucher voucher) {
		Long voucherId = dsl.insertInto(table(VOUCHERS))
				.set(field("voucher_no"), voucher.getVoucherNo())
				.set(field("voucher_date"), voucher.getVoucherDate())
				.set(field("description"), voucher.getDescription())
				.set(field("status"), voucher.getStatus())
				.set(field("ifrs_basis"), voucher.getIfrsBasis())
				.returningResult(field(ID, Long.class))
				.fetchOne(field(ID, Long.class));
		voucher.setId(voucherId);

		List<VoucherEntry> entries = voucher.getEntries();
		if (entries != null) {
			for (VoucherEntry entry : entries) {
				Long entryId = dsl.insertInto(table(ENTRIES))
						.set(field(VOUCHER_ID), voucherId)
						.set(field("account_id"), entry.getAccount().getId())
						.set(field("debit"), entry.getDebit())
						.set(field("credit"), entry.getCredit())
						.set(field("remark"), entry.getRemark())
						.returningResult(field(ID, Long.class))
						.fetchOne(field(ID, Long.class));
				entry.setId(entryId);
				entry.setVoucher(voucher);
			}
		}
		return voucher;
	}
}
