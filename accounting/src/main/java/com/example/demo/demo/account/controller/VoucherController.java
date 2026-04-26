package com.example.demo.demo.account.controller;

import com.example.demo.demo.account.model.Voucher;
import com.example.demo.demo.account.service.impl.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    @PostMapping("create")
    public Map<String, String> create(@RequestBody Voucher voucher) {
        Voucher saved = voucherService.createVoucher(voucher);
        return Map.of("id", saved.getId().toString(), "voucherNo", saved.getVoucherNo());
    }
}
