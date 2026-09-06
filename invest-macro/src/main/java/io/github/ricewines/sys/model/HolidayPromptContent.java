package io.github.ricewines.sys.model;

import lombok.Data;
import lombok.experimental.Accessors;

/// 休市内容
@Accessors(chain = true)
@Data
public class HolidayPromptContent {
    /// 休市安排内容
    private String content;
}
