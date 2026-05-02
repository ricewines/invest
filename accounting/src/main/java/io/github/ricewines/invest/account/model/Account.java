package io.github.ricewines.invest.account.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Table(name = "accounts")
@Accessors(chain = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // 科目代码 1001 1002...

    private String name; // 科目名称

    private String type; // 资产/负债/权益/收入/费用 IAS1
    private String ifrsStandard; // 对应IFRS/IAS准则
}
