package com.zigaai.authentication.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.authentication.mapper.AdminMapper;
import com.zigaai.authentication.service.AdminService;
import com.zigaai.authentication.model.convertor.AdminConvertor;
import com.zigaai.authentication.model.dto.command.AdminDTO;
import com.zigaai.authentication.model.dto.query.AdminQuery;
import com.zigaai.authentication.model.entity.Admin;
import com.zigaai.authentication.model.vo.AdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public IPage<AdminVO> page(AdminQuery params) {
        Page<AdminVO> page = new Page<>();
        List<AdminVO> records = adminMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.insert(entity);
    }

    @Override
    public int update(AdminDTO data) {
        Admin entity = AdminConvertor.INSTANCE.toEntity(data);
        return adminMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }

}
