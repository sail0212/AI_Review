package com.aireview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 主题增改入参。
 */
@Data
public class ThemeRequest {

    @NotBlank(message = "主题名不能为空")
    @Size(max = 64, message = "主题名过长")
    private String name;

    /** JSON 字符串：{ bg, fg, accent, font } */
    private String config;

    private Boolean isDefault;
}
