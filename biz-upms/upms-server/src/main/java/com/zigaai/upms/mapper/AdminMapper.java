package com.zigaai.upms.mapper;

import com.zigaai.upms.model.entity.Admin;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.upms.model.vo.AdminVO;
import com.zigaai.upms.model.dto.query.AdminQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
