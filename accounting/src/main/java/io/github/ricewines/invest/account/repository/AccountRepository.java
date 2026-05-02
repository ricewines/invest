package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
