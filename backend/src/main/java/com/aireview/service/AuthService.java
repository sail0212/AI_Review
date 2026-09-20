package com.aireview.service;

import com.aireview.dto.LoginRequest;
import com.aireview.dto.LoginResponse;
import com.aireview.dto.RegisterRequest;
import com.aireview.dto.UserVO;
import com.aireview.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AuthService extends IService<User> {

    LoginResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserVO me();
}
