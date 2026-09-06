package io.github.ricewines.sys.service.impl;

import ai.z.openapi.ZhipuAiClient;
import ai.z.openapi.service.model.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.ricewines.sys.config.InvestConfig;
import io.github.ricewines.sys.controller.SseClosedScheduleController;
import io.github.ricewines.sys.model.HolidayPromptContent;
import io.github.ricewines.sys.model.MarketHoliday;
import io.github.ricewines.sys.service.SseClosedScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/// 上交所休市安排
@Slf4j
@Service
@RestController
@AllArgsConstructor
public class SseClosedScheduleServiceImpl implements SseClosedScheduleService, SseClosedScheduleController {

    private final Configuration freemarkerConfiguration;
    private final InvestConfig investConfig;
    private final ObjectMapper objectMapper;

    @Override
    public List<MarketHoliday> fetchClosedSchedule() {
        String pageHtml = fetchClosedSchedulePage();
        String prompt;
        try {
            prompt = FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("invest/email/上交所休市安排获取提示词.md.ftl"),
                    new HolidayPromptContent().setContent(pageHtml)
            );
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }

        ChatCompletionResponse response = ZhipuAiClient.builder().ofZHIPU().apiKey(investConfig.getZhiPuAi().getApiKey()).build().chat()
                .createChatCompletion(ChatCompletionCreateParams.builder()
                        .model(investConfig.getZhiPuAi().getModel())
                        .messages(List.of(ChatMessage.builder().role(ChatMessageRole.USER.value()).content(prompt).build()))
                        .thinking(ChatThinking.builder().type("enabled").build())
                        .build());

        if (!response.isSuccess()) {
            throw new IllegalStateException("AI 请求失败: " + response);
        }

        String json = response.getData().getChoices().getFirst().getMessage().getContent() + "";
        return objectMapper.readValue(json, new TypeReference<>() {});
    }

    private String fetchClosedSchedulePage() {
        HttpURLConnection connection = null;
        try {
            URL url = URI.create("https://www.sse.com.cn/disclosure/dealinstruc/closed/").toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0 Safari/537.36");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");

            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 400) {
                throw new IllegalStateException("请求上交所休市安排页面失败，状态码: " + statusCode);
            }

            StringBuilder content = new StringBuilder();
            try (Reader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                char[] buffer = new char[4096];
                int read;
                while ((read = reader.read(buffer)) != -1) {
                    content.append(buffer, 0, read);
                }
            }
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException("读取上交所休市安排页面失败", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
