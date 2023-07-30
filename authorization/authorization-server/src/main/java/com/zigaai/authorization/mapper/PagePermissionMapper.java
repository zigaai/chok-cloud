package com.zigaai.authorization.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.authorization.model.dto.query.PagePermissionQuery;
import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.vo.PagePermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 页面权限表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface PagePermissionMapper extends MapperSupport<PagePermission> {

    List<PagePermissionVO> listByCondition(IPage<PagePermissionVO> page, @Param("params") PagePermissionQuery params, @Param("columns") String... columns);

    List<PagePermission> listByRoleIds(@Param("roleIds") Collection<Long> roleIds);
}
