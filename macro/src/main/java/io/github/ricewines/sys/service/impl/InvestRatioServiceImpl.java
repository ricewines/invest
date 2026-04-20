package io.github.ricewines.sys.service.impl;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.*;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.ricewines.sys.config.InvestConfig;
import io.github.ricewines.sys.controller.InvestRatioController;
import io.github.ricewines.sys.model.InvestmentStrategy;
import io.github.ricewines.sys.service.InvestRatioService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/// 投资比例
@Slf4j
@Service
@RestController
@AllArgsConstructor
public class InvestRatioServiceImpl implements InvestRatioService, InvestRatioController {

    /// 应用上下文
    private ApplicationContext applicationContext;
    /// 邮件发送者
    private JavaMailSender javaMailSender;
    /// 投资配置
    private InvestConfig investConfig;

    /// freemarker模板
    private Configuration freemarkerConfiguration;
    /// 序列化工具
    private ObjectMapper jacksonObjectMapper;

    /**
     * 加仓和调仓
     */
    @Override
    @Scheduled(cron = "0 20 8 5 * *")
//    @Scheduled(cron = "0 41 22 23 3 *")
    public void positionAddAndAdjust() {

        String taskName = "加仓和调仓的投资比例";
        StopWatch stopWatch = StopWatch.create(taskName);
        stopWatch.start();
        // 创建聊天完成请求
        // 发送请求
        try {
            InputStream inputStream = applicationContext.getResource("classpath:投资比例提示词.md").getInputStream();

            ChatCompletionResponse response = ZhipuAiClient.builder().ofZHIPU().apiKey(investConfig.getZhiPuAi().getApiKey()).build().chat().createChatCompletion(ChatCompletionCreateParams.builder().model(investConfig.getZhiPuAi().getModel()).messages(List.of(ChatMessage.builder().role(ChatMessageRole.USER.value()).content(IoUtil.readUtf8(inputStream)).build())).thinking(ChatThinking.builder().type("enabled").build()).maxTokens(65536).temperature(1.0f).build());

            // 获取回复
            if (response.isSuccess()) {
                ChatMessage reply = response.getData().getChoices().getFirst().getMessage();
                log.info("AI 回复: {}", reply);
                // 邮件模板做得精美些
                InvestmentStrategy investmentStrategy = jacksonObjectMapper.readValue(reply.getContent() + "", InvestmentStrategy.class);
                String markdownContent = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("invest/投资比例邮件正文模板.md"), investmentStrategy);
                List<Extension> extensions = Arrays.asList(TablesExtension.create(), StrikethroughExtension.create());
                String htmlContent = HtmlRenderer.builder().extensions(extensions).build().render(Parser.builder().extensions(extensions).build().parse(markdownContent));
                String subject = "%s投资比例提示".formatted(LocalDate.now().format(DateTimeFormatter.ofPattern("yy年MM月", Locale.CHINA)));
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();

                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom("qazcxh@163.com"); // 发件人邮箱地址，需要与配置文件中的一致或使用其他合法邮箱地址。
                mimeMessageHelper.setTo(investConfig.getMailConfig().getTo()); // 收件人邮箱地址。可以多个收件人，用逗号隔开。如："aaa@bbb.com, ccc@ddd.com"
                mimeMessageHelper.setSubject(subject); // 邮件主题（标题） 与htmlContent配合来展示。
                mimeMessageHelper.setText(htmlContent, true); // true表示html格式邮件。第一个参数是邮件正文内容，第二个参数表示是否是html内容。
                javaMailSender.send(mimeMessage);
                log.info("邮件发送成功");
            } else {
                log.error("{}", response);
            }
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {

            stopWatch.stop();
            log.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));

        }
    }
}
