package com.zigaai.authorization.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.authorization.model.dto.query.AdminQuery;
import com.zigaai.authorization.model.entity.Admin;
import com.zigaai.authorization.model.vo.AdminVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface AdminMapper extends MapperSupport<Admin> {

    List<AdminVO> listByCondition(IPage<AdminVO> page, @Param("params") AdminQuery params, @Param("columns") String... columns);

    Admin getByUsername(String username);

    String getSaltByUsername(String username);

}
