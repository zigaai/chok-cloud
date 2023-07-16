package com.zigaai.upms.mapper;

import com.zigaai.upms.model.entity.Role;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.upms.model.enumeration.SysUserType;
import com.zigaai.upms.model.vo.RoleVO;
import com.zigaai.upms.model.dto.query.RoleQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
