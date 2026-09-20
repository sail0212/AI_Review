package com.aireview.service.impl;

import com.aireview.common.BusinessException;
import com.aireview.common.ResultCode;
import com.aireview.dto.TagRequest;
import com.aireview.dto.TagVO;
import com.aireview.entity.NoteTag;
import com.aireview.entity.Tag;
import com.aireview.mapper.NoteTagMapper;
import com.aireview.mapper.TagMapper;
import com.aireview.service.TagService;
import com.aireview.util.UserContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final NoteTagMapper noteTagMapper;

    @Override
    public List<TagVO> listMine() {
        return lambdaQuery().eq(Tag::getUserId, UserContext.getUserId()).list().stream()
                .map(tag -> new TagVO(tag.getId(), tag.getName()))
                .toList();
    }

    @Override
    public TagVO create(TagRequest request) {
        Long userId = UserContext.getUserId();
        boolean exists = lambdaQuery()
                .eq(Tag::getUserId, userId)
                .eq(Tag::getName, request.getName())
                .exists();
        if (exists) {
            throw new BusinessException(ResultCode.BAD_REQUEST.getCode(), "标签已存在");
        }
        Tag tag = new Tag();
        tag.setUserId(userId);
        tag.setName(request.getName());
        save(tag);
        return new TagVO(tag.getId(), tag.getName());
    }

    @Override
    public TagVO rename(Long id, TagRequest request) {
        Tag tag = getOwnedTag(id);
        tag.setName(request.getName());
        updateById(tag);
        return new TagVO(tag.getId(), tag.getName());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getOwnedTag(id);
        removeById(id);
        noteTagMapper.delete(new LambdaQueryWrapper<NoteTag>().eq(NoteTag::getTagId, id));
    }

    private Tag getOwnedTag(Long id) {
        Tag tag = getById(id);
        if (tag == null || !tag.getUserId().equals(UserContext.getUserId())) {
            throw new BusinessException(ResultCode.NOT_FOUND);
        }
        return tag;
    }
}
