package com.zigaai.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.authorization.model.dto.command.PagePermissionDTO;
import com.zigaai.authorization.model.dto.query.PagePermissionQuery;
import com.zigaai.authorization.model.vo.PagePermissionVO;

/**
 * <p>
 * 页面权限表 服务类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface PagePermissionService {

    IPage<PagePermissionVO> page(PagePermissionQuery params);

    int add(PagePermissionDTO data);

    int update(PagePermissionDTO data);

    int deleteById(Long id);

}
