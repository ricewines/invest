package io.github;

import io.github.ricewines.invest.account.model.Account;
import io.github.ricewines.invest.account.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountingsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingsApplication.class, args);
    }

    @Bean
    CommandLineRunner initAllIFRSAccounts(AccountRepository repo) {
        return _ -> {
            if (repo.count() == 0) {

                // ==============================
                // 一、资产类 (Assets) - IAS 1
                // ==============================
                repo.save(newAccount("1001", "现金及现金等价物", "资产", "IAS 1"));
                repo.save(newAccount("1002", "银行存款", "资产", "IAS 1"));
                repo.save(newAccount("1003", "短期投资", "资产", "IAS 32"));
                repo.save(newAccount("1101", "应收账款", "资产", "IAS 1"));
                repo.save(newAccount("1102", "坏账准备", "资产", "IAS 37"));
                repo.save(newAccount("1201", "存货", "资产", "IAS 2"));
                repo.save(newAccount("1301", "预付款项", "资产", "IAS 1"));
                repo.save(newAccount("1401", "其他应收款", "资产", "IAS 1"));
                repo.save(newAccount("1501", "长期股权投资", "资产", "IAS 27"));
                repo.save(newAccount("1601", "固定资产", "资产", "IAS 16"));
                repo.save(newAccount("1602", "累计折旧", "资产", "IAS 16"));
                repo.save(newAccount("1701", "无形资产", "资产", "IAS 38"));
                repo.save(newAccount("1702", "累计摊销", "资产", "IAS 38"));
                repo.save(newAccount("1801", "商誉", "资产", "IFRS 3"));
                repo.save(newAccount("1901", "递延所得税资产", "资产", "IAS 12"));

                // ==============================
                // 二、负债类 (Liabilities) - IAS 1
                // ==============================
                repo.save(newAccount("2001", "短期借款", "负债", "IAS 1"));
                repo.save(newAccount("2101", "应付账款", "负债", "IAS 1"));
                repo.save(newAccount("2201", "应付职工薪酬", "负债", "IAS 19"));
                repo.save(newAccount("2301", "应交税费", "负债", "IAS 12"));
                repo.save(newAccount("2401", "预收款项", "负债", "IAS 1"));
                repo.save(newAccount("2501", "其他应付款", "负债", "IAS 1"));
                repo.save(newAccount("2601", "长期借款", "负债", "IAS 1"));
                repo.save(newAccount("2701", "应付债券", "负债", "IAS 32"));
                repo.save(newAccount("2801", "预计负债", "负债", "IAS 37"));
                repo.save(newAccount("2901", "递延所得税负债", "负债", "IAS 12"));
                repo.save(newAccount("2902", "租赁负债", "负债", "IFRS 16"));

                // ==============================
                // 三、权益类 (Equity) - IAS 1
                // ==============================
                repo.save(newAccount("3001", "实收资本", "权益", "IAS 1"));
                repo.save(newAccount("3101", "资本公积", "权益", "IAS 1"));
                repo.save(newAccount("3201", "其他综合收益", "权益", "IAS 1"));
                repo.save(newAccount("3301", "盈余公积", "权益", "IAS 1"));
                repo.save(newAccount("3401", "未分配利润", "权益", "IAS 1"));

                // ==============================
                // 四、收入类 (Income) - IFRS 15
                // ==============================
                repo.save(newAccount("4001", "主营业务收入", "收入", "IFRS 15"));
                repo.save(newAccount("4101", "其他业务收入", "收入", "IAS 1"));
                repo.save(newAccount("4201", "投资收益", "收入", "IAS 18"));
                repo.save(newAccount("4301", "公允价值变动收益", "收入", "IFRS 9"));
                repo.save(newAccount("4401", "营业外收入", "收入", "IAS 1"));

                // ==============================
                // 五、费用类 (Expenses) - IAS 1
                // ==============================
                repo.save(newAccount("5001", "主营业务成本", "费用", "IAS 1"));
                repo.save(newAccount("5101", "其他业务成本", "费用", "IAS 1"));
                repo.save(newAccount("5201", "销售费用", "费用", "IAS 1"));
                repo.save(newAccount("5301", "管理费用", "费用", "IAS 1"));
                repo.save(newAccount("5401", "财务费用", "费用", "IAS 1"));
                repo.save(newAccount("5501", "资产减值损失", "费用", "IAS 36"));
                repo.save(newAccount("5601", "所得税费用", "费用", "IAS 12"));
                repo.save(newAccount("5701", "营业外支出", "费用", "IAS 1"));

                System.out.println("✅ IFRS 全套会计科目初始化完成");
            }
        };
    }

    // 工具方法：快速创建科目
    private Account newAccount(String code, String name, String type, String ifrsStandard) {
        Account account = new Account();
        account.setCode(code);
        account.setName(name);
        account.setType(type);
        account.setIfrsStandard(ifrsStandard);
        return account;
    }
}
