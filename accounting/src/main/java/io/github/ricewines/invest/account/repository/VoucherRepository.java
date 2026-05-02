package io.github.ricewines.invest.account.repository;

import io.github.ricewines.invest.account.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
