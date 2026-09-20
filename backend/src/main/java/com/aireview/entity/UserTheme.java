package com.aireview.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户自定义主题实体。
 */
@Data
@TableName("user_theme")
public class UserTheme {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    /** JSON 字符串：{ bg, fg, accent, font } */
    private String config;

    private Boolean isDefault;

    private LocalDateTime createdAt;
}
