package com.example.demo.demo.account.repository;

import com.example.demo.demo.account.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}
