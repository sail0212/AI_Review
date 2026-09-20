package com.aireview.controller;

import com.aireview.common.Result;
import com.aireview.dto.ThemeRequest;
import com.aireview.dto.ThemeVO;
import com.aireview.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public Result<List<ThemeVO>> list() {
        return Result.ok(themeService.listMine());
    }

    @PostMapping
    public Result<ThemeVO> create(@Valid @RequestBody ThemeRequest request) {
        return Result.ok(themeService.create(request));
    }

    @PutMapping("/{id}")
    public Result<ThemeVO> update(@PathVariable Long id, @Valid @RequestBody ThemeRequest request) {
        return Result.ok(themeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        themeService.delete(id);
        return Result.ok();
    }
}
