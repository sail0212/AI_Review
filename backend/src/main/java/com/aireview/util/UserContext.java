package com.aireview.util;

/**
 * 当前登录用户上下文，基于 ThreadLocal，由 JWT 拦截器写入、请求结束后清理。
 */
public final class UserContext {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    private UserContext() {
    }

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    /**
     * 获取当前登录用户 ID。
     *
     * @throws IllegalStateException 未登录时抛出
     */
    public static Long getUserId() {
        Long userId = USER_ID.get();
        if (userId == null) {
            throw new IllegalStateException("未获取到当前登录用户");
        }
        return userId;
    }

    public static void clear() {
        USER_ID.remove();
    }
}
