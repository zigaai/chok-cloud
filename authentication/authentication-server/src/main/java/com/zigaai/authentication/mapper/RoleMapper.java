package com.zigaai.authentication.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.authentication.model.dto.query.RoleQuery;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface RoleMapper extends MapperSupport<Role> {

    List<RoleVO> listByCondition(IPage<RoleVO> page, @Param("params") RoleQuery params, @Param("columns") String... columns);

    List<Role> listBySysUserId(@Param("userId") Long userId, @Param("userType") SysUserType userType);

}
