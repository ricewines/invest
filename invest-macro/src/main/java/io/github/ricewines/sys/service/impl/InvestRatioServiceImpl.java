package io.github.ricewines.sys.service.impl;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.*;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.ricewines.sys.config.InvestConfig;
import io.github.ricewines.sys.constant.EmailSubscriptionTemporal;
import io.github.ricewines.sys.controller.InvestRatioController;
import io.github.ricewines.sys.model.InvestRatioMailModel;
import io.github.ricewines.sys.model.InvestmentStrategy;
import io.github.ricewines.sys.service.InvestRatioService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.github.ricewines.sys.model.SubscribeConstant.*;

/// 投资比例
@Slf4j
@Service
@RestController
@AllArgsConstructor
public class InvestRatioServiceImpl implements InvestRatioService, InvestRatioController {

    /// 应用上下文
    private ApplicationContext applicationContext;
    /// freemarker模板
    private Configuration freemarkerConfiguration;
    /// DSL上下文
    private DSLContext dslContext;
    /// 环境
    private Environment environment;
    /// 投资配置
    private InvestConfig investConfig;
    /// 邮件发送者
    private JavaMailSender javaMailSender;
    /// 序列化工具
    private ObjectMapper objectMapper;

    /**
     * 加仓和调仓
     */
    @Override
    public void positionAddAndAdjust() {

        // 创建聊天完成请求
        // 发送请求
        InputStream inputStream;
        try {
            inputStream = applicationContext.getResource("classpath:invest/prompt/投资比例提示词.md").getInputStream();

            ChatCompletionResponse response = ZhipuAiClient.builder().ofZHIPU().apiKey(investConfig.getZhiPuAi().getApiKey()).build().chat()
                    .createChatCompletion(ChatCompletionCreateParams.builder().model(investConfig.getZhiPuAi().getModel())
                            .messages(List.of(ChatMessage.builder().role(ChatMessageRole.USER.value()).content(IoUtil.readUtf8(inputStream)).build()))
                            .thinking(ChatThinking.builder().type("enabled").build()).build());

            // 获取回复
            if (response.isSuccess()) {
                ChatMessage reply = response.getData().getChoices().getFirst().getMessage();
                log.info("AI 回复: {}", reply);
                // 邮件模板做得精美些：直接使用 HTML 模板，避免 markdown -> html 的重复转换
                InvestmentStrategy investmentStrategy = objectMapper.readValue(reply.getContent() + "", InvestmentStrategy.class);
                String bizTime = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA));
                InvestRatioMailModel baseModel = InvestRatioMailModel.builder()
                        .bizTime(bizTime)
                        .currencyRatio(formatRatio(investmentStrategy.getCurrencyRatio()))
                        .bondRatio(formatRatio(investmentStrategy.getBondRatio()))
                        .stockRatio(formatRatio(investmentStrategy.getStockRatio()))
                        .commodityRatio(formatRatio(investmentStrategy.getCommodityRatio()))
                        .build();
                String subject = "%s投资比例提示".formatted(LocalDate.now().format(DateTimeFormatter.ofPattern("yy年MM月", Locale.CHINA)));
                String domain = investConfig.getPageConfig().getDomain();

                Result<Record2<String, String>> fetch = dslContext.select(EmailSubscriptionTemporal.EMAIL, EmailSubscriptionTemporal.TOKEN)
                        .from(EmailSubscriptionTemporal.TABLE)
                        .where(EmailSubscriptionTemporal.SUB_TYPE.eq(SUB_TYPE_INVEST_RATIO))
                        .and(EmailSubscriptionTemporal.STATUS.eq(STATUS_ACTIVE))
                        .and(EmailSubscriptionTemporal.VALID_TO.isNull()).fetch();
                List<Map.Entry<String, String>> recipients = new ArrayList<>();
                if (CollUtil.isEmpty(fetch)) {
                    recipients.add(new AbstractMap.SimpleEntry<>(investConfig.getMailConfig().getTo(), "sub_default"));
                } else {
                    fetch.forEach(n -> recipients.add(new AbstractMap.SimpleEntry<>(
                            n.get(EmailSubscriptionTemporal.EMAIL),
                            n.get(EmailSubscriptionTemporal.TOKEN)
                    )));
                }

                for (Map.Entry<String, String> recipient : recipients) {
                    String to = recipient.getKey();
                    String unsubscribeToken = recipient.getValue();
                    InvestRatioMailModel model = InvestRatioMailModel.builder()
                            .bizTime(baseModel.getBizTime())
                            .currencyRatio(baseModel.getCurrencyRatio())
                            .bondRatio(baseModel.getBondRatio())
                            .stockRatio(baseModel.getStockRatio())
                            .commodityRatio(baseModel.getCommodityRatio())
                            .unsubscribeUrl(domain + "/invest-admin-page/config?token=" + unsubscribeToken)
                            .build();
                    String emailHtmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration
                            .getTemplate("invest/email/invest_ratio_mail.ftl"), model);
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setFrom(Objects.requireNonNull(environment.getProperty("spring.mail.username")));
                    mimeMessageHelper.setSubject(subject); // 邮件主题（标题） 与htmlContent配合来展示。
                    mimeMessageHelper.setText(emailHtmlContent, true); // true表示html格式邮件。第一个参数是邮件正文内容，第二个参数表示是否是html内容。
                    mimeMessageHelper.setTo(to); // 收件人邮箱地址。可以多个收件人，用逗号隔开。如："aaa@bbb.com, ccc@ddd.com"

                    javaMailSender.send(mimeMessage);
                    log.info("邮件发送成功，收件人: {}, 主题: {}", to, subject);
                }

            } else {
                log.error("{}", response);
            }
        } catch (IOException | MessagingException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatRatio(Double ratio) {
        if (ratio == null) {
            return "0.00";
        }
        return String.format(Locale.CHINA, "%.2f", ratio * 100);
    }
}
