package com.aireview.service.impl;

import com.aireview.common.BusinessException;
import com.aireview.common.ResultCode;
import com.aireview.dto.ThemeRequest;
import com.aireview.dto.ThemeVO;
import com.aireview.entity.UserTheme;
import com.aireview.mapper.UserThemeMapper;
import com.aireview.service.ThemeService;
import com.aireview.util.UserContext;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl extends ServiceImpl<UserThemeMapper, UserTheme> implements ThemeService {

    @Override
    public List<ThemeVO> listMine() {
        return lambdaQuery().eq(UserTheme::getUserId, UserContext.getUserId()).list().stream()
                .map(this::toVO)
                .toList();
    }

    @Override
    @Transactional
    public ThemeVO create(ThemeRequest request) {
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefault(UserContext.getUserId());
        }
        UserTheme theme = new UserTheme();
        theme.setUserId(UserContext.getUserId());
        theme.setName(request.getName());
        theme.setConfig(request.getConfig());
        theme.setIsDefault(request.getIsDefault());
        save(theme);
        return toVO(theme);
    }

    @Override
    @Transactional
    public ThemeVO update(Long id, ThemeRequest request) {
        UserTheme theme = getOwnedTheme(id);
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefault(UserContext.getUserId());
        }
        theme.setName(request.getName());
        theme.setConfig(request.getConfig());
        theme.setIsDefault(request.getIsDefault());
        updateById(theme);
        return toVO(theme);
    }

    @Override
    public void delete(Long id) {
        getOwnedTheme(id);
        removeById(id);
    }

    private UserTheme getOwnedTheme(Long id) {
        UserTheme theme = getById(id);
        if (theme == null || !theme.getUserId().equals(UserContext.getUserId())) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return theme;
    }

    private void clearDefault(Long userId) {
        update(new LambdaUpdateWrapper<UserTheme>()
                .eq(UserTheme::getUserId, userId)
                .set(UserTheme::getIsDefault, false));
    }

    private ThemeVO toVO(UserTheme theme) {
        ThemeVO vo = new ThemeVO();
        vo.setId(theme.getId());
        vo.setName(theme.getName());
        vo.setConfig(theme.getConfig());
        vo.setIsDefault(theme.getIsDefault());
        vo.setCreatedAt(theme.getCreatedAt());
        return vo;
    }
}
