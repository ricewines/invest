package com.example.demo.demo.account.repository;

import com.example.demo.demo.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
