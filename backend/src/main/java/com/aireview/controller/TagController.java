package com.aireview.controller;

import com.aireview.common.Result;
import com.aireview.dto.TagRequest;
import com.aireview.dto.TagVO;
import com.aireview.service.TagService;
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
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public Result<List<TagVO>> list() {
        return Result.ok(tagService.listMine());
    }

    @PostMapping
    public Result<TagVO> create(@Valid @RequestBody TagRequest request) {
        return Result.ok(tagService.create(request));
    }

    @PutMapping("/{id}")
    public Result<TagVO> rename(@PathVariable Long id, @Valid @RequestBody TagRequest request) {
        return Result.ok(tagService.rename(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return Result.ok();
    }
}
