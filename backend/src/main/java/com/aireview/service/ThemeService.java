package com.aireview.service;

import com.aireview.dto.ThemeRequest;
import com.aireview.dto.ThemeVO;
import com.aireview.entity.UserTheme;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ThemeService extends IService<UserTheme> {

    List<ThemeVO> listMine();

    ThemeVO create(ThemeRequest request);

    ThemeVO update(Long id, ThemeRequest request);

    void delete(Long id);
}
