package com.zigaai.authorization.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.infra.mapper.MapperSupport;
import com.zigaai.authorization.model.dto.query.UserQuery;
import com.zigaai.authorization.model.entity.User;
import com.zigaai.authorization.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface UserMapper extends MapperSupport<User> {

    List<UserVO> listByCondition(IPage<UserVO> page, @Param("params") UserQuery params, @Param("columns") String... columns);

    User getByUsername(String username);

    String getSaltByUsername(String username);

}
