package com.aireview.controller;

import com.aireview.common.Result;
import com.aireview.dto.NoteUpdateRequest;
import com.aireview.dto.NoteVO;
import com.aireview.dto.ShareCreateRequest;
import com.aireview.dto.ShareVO;
import com.aireview.service.ShareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShareController {

    private final ShareService shareService;

    @PostMapping("/api/notes/{id}/share")
    public Result<ShareVO> create(@PathVariable Long id,
                                  @RequestBody(required = false) ShareCreateRequest request) {
        return Result.ok(shareService.create(id, request));
    }

    @GetMapping("/api/notes/{id}/shares")
    public Result<List<ShareVO>> listByNote(@PathVariable Long id) {
        return Result.ok(shareService.listByNote(id));
    }

    @DeleteMapping("/api/shares/{id}")
    public Result<Void> revoke(@PathVariable Long id) {
        shareService.revoke(id);
        return Result.ok();
    }

    @GetMapping("/api/share/{token}")
    public Result<NoteVO> getByToken(@PathVariable String token) {
        return Result.ok(shareService.getByToken(token));
    }

    @PutMapping("/api/share/{token}")
    public Result<NoteVO> updateByToken(@PathVariable String token,
                                        @Valid @RequestBody NoteUpdateRequest request) {
        return Result.ok(shareService.updateByToken(token, request));
    }
}
