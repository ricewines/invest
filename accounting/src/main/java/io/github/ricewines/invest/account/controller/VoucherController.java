package io.github.ricewines.invest.account.controller;

import io.github.ricewines.invest.account.model.Voucher;
import io.github.ricewines.invest.account.service.impl.VoucherService;
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
