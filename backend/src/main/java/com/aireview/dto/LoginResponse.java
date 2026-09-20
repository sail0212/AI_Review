package com.aireview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录返回。
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private Long userId;

    private String username;

    private String nickname;
}
