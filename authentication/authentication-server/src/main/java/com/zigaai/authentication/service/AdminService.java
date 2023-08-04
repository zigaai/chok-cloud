package com.zigaai.authentication.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.authentication.model.dto.command.AdminDTO;
import com.zigaai.authentication.model.dto.query.AdminQuery;
import com.zigaai.authentication.model.vo.AdminVO;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface AdminService {

    IPage<AdminVO> page(AdminQuery params);

    int add(AdminDTO data);

    int update(AdminDTO data);

    int deleteById(Long id);

}
