package com.zigaai.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.authorization.model.dto.command.RoleDTO;
import com.zigaai.authorization.model.dto.query.RoleQuery;
import com.zigaai.authorization.model.vo.RoleVO;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface RoleService {

    IPage<RoleVO> page(RoleQuery params);

    int add(RoleDTO data);

    int update(RoleDTO data);

    int deleteById(Long id);

}
