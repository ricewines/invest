package io.github.ricewines.invest.account.controller;

import io.github.ricewines.invest.account.model.Account;
import io.github.ricewines.invest.account.service.impl.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create")
    public Account create(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("accounts")
    public List<Account> list() {
        return accountService.getAll();
    }

}