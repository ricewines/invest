package com.example.demo.demo.account.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter  // 只留 getter
@Setter  // 只留 setter
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String voucherNo;
    private LocalDate voucherDate;
    private String description;
    private String status;

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<VoucherEntry> entries;

    private String ifrsBasis;
}