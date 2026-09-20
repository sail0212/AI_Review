package com.aireview.controller;

import com.aireview.common.PageResult;
import com.aireview.common.Result;
import com.aireview.dto.NoteCreateRequest;
import com.aireview.dto.NoteQueryRequest;
import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public Result<PageResult<NoteVO>> page(NoteQueryRequest request) {
        return Result.ok(noteService.page(request));
    }

    @PostMapping
    public Result<NoteVO> create(@Valid @RequestBody NoteCreateRequest request) {
        return Result.ok(noteService.create(request));
    }

    @GetMapping("/{id}")
    public Result<NoteVO> detail(@PathVariable Long id) {
        return Result.ok(noteService.detail(id));
    }

    @PutMapping("/{id}")
    public Result<NoteVO> update(@PathVariable Long id, @Valid @RequestBody NoteUpdateRequest request) {
        return Result.ok(noteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return Result.ok();
    }

    @PatchMapping("/{id}/pin")
    public Result<NoteVO> togglePin(@PathVariable Long id) {
        return Result.ok(noteService.togglePin(id));
    }

    @PatchMapping("/{id}/archive")
    public Result<NoteVO> toggleArchive(@PathVariable Long id) {
        return Result.ok(noteService.toggleArchive(id));
    }
}
