package com.zigaai.authentication.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.authentication.model.dto.command.UserDTO;
import com.zigaai.authentication.model.dto.query.UserQuery;
import com.zigaai.authentication.model.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
public interface UserService {

    IPage<UserVO> page(UserQuery params);

    int add(UserDTO data);

    int update(UserDTO data);

    int deleteById(Long id);

}
