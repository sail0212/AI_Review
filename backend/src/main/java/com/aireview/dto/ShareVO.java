package com.aireview.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分享返回。
 */
@Data
public class ShareVO {

    private Long id;

    private String token;

    private String mode;

    private LocalDateTime expiresAt;

    private String url;
}
