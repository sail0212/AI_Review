package com.aireview.service;

import com.aireview.dto.TagRequest;
import com.aireview.dto.TagVO;
import com.aireview.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagVO> listMine();

    TagVO create(TagRequest request);

    TagVO rename(Long id, TagRequest request);

    void delete(Long id);
}
