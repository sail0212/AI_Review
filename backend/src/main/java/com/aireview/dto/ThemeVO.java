package com.aireview.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 主题返回。
 */
@Data
public class ThemeVO {

    private Long id;

    private String name;

    /** JSON 字符串：{ bg, fg, accent, font } */
    private String config;

    private Boolean isDefault;

    private LocalDateTime createdAt;
}
