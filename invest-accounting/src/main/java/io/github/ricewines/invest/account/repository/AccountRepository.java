package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
	private static final String TABLE = "accounts";
	private static final String ID = "id";
	private static final String CODE = "code";
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String IFRS_STANDARD = "ifrs_standard";

	private final DSLContext dsl;

	public Account save(Account account) {
		if (account.getId() == null) {
			Long id = dsl.insertInto(table(TABLE))
					.set(field(CODE), account.getCode())
					.set(field(NAME), account.getName())
					.set(field(TYPE), account.getType())
					.set(field(IFRS_STANDARD), account.getIfrsStandard())
					.returningResult(field(ID, Long.class))
					.fetchOne(field(ID, Long.class));
			account.setId(id);
			return account;
		}

		dsl.update(table(TABLE))
				.set(field(CODE), account.getCode())
				.set(field(NAME), account.getName())
				.set(field(TYPE), account.getType())
				.set(field(IFRS_STANDARD), account.getIfrsStandard())
				.where(field(ID).eq(account.getId()))
				.execute();
		return account;
	}

	public List<Account> findAll() {
		return dsl.select()
				.from(table(TABLE))
				.orderBy(field(CODE))
				.fetchInto(Account.class);
	}
}
