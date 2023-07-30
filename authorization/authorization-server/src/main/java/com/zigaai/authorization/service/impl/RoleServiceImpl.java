package com.zigaai.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.authorization.mapper.RoleMapper;
import com.zigaai.authorization.model.convertor.RoleConvertor;
import com.zigaai.authorization.model.dto.command.RoleDTO;
import com.zigaai.authorization.model.dto.query.RoleQuery;
import com.zigaai.authorization.model.entity.Role;
import com.zigaai.authorization.model.vo.RoleVO;
import com.zigaai.authorization.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    public IPage<RoleVO> page(RoleQuery params) {
        Page<RoleVO> page = new Page<>();
        List<RoleVO> records = roleMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(RoleDTO data) {
        Role entity = RoleConvertor.INSTANCE.toEntity(data);
        return roleMapper.insert(entity);
    }

    @Override
    public int update(RoleDTO data) {
        Role entity = RoleConvertor.INSTANCE.toEntity(data);
        return roleMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return roleMapper.deleteById(id);
    }

}
