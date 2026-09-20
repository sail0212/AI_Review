package com.aireview.config;

import com.aireview.util.JwtUtil;
import com.aireview.util.UserContext;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 鉴权拦截器：解析请求头 {@code Authorization: Bearer <token>}，写入 {@link UserContext}。
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return reject(response);
        }
        String token = header.substring(BEARER_PREFIX.length());
        try {
            Long userId = jwtUtil.parseToken(token);
            UserContext.setUserId(userId);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return reject(response);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserContext.clear();
    }

    private boolean reject(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return false;
    }
}
