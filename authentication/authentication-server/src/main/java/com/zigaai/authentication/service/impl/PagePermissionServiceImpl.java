package com.zigaai.authentication.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.authentication.mapper.PagePermissionMapper;
import com.zigaai.authentication.service.PagePermissionService;
import com.zigaai.authentication.model.convertor.PagePermissionConvertor;
import com.zigaai.authentication.model.dto.command.PagePermissionDTO;
import com.zigaai.authentication.model.dto.query.PagePermissionQuery;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.vo.PagePermissionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 页面权限表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Service
@RequiredArgsConstructor
public class PagePermissionServiceImpl implements PagePermissionService {

    private final PagePermissionMapper pagePermissionMapper;

    @Override
    public IPage<PagePermissionVO> page(PagePermissionQuery params) {
        Page<PagePermissionVO> page = new Page<>();
        List<PagePermissionVO> records = pagePermissionMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(PagePermissionDTO data) {
        PagePermission entity = PagePermissionConvertor.INSTANCE.toEntity(data);
        return pagePermissionMapper.insert(entity);
    }

    @Override
    public int update(PagePermissionDTO data) {
        PagePermission entity = PagePermissionConvertor.INSTANCE.toEntity(data);
        return pagePermissionMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return pagePermissionMapper.deleteById(id);
    }

}
