package com.zigaai.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zigaai.authorization.mapper.UserMapper;
import com.zigaai.authorization.model.convertor.UserConvertor;
import com.zigaai.authorization.model.dto.command.UserDTO;
import com.zigaai.authorization.model.dto.query.UserQuery;
import com.zigaai.authorization.model.entity.User;
import com.zigaai.authorization.model.vo.UserVO;
import com.zigaai.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public IPage<UserVO> page(UserQuery params) {
        Page<UserVO> page = new Page<>();
        List<UserVO> records = userMapper.listByCondition(page, params);
        page.setRecords(records);
        return page;
    }

    @Override
    public int add(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.insert(entity);
    }

    @Override
    public int update(UserDTO data) {
        User entity = UserConvertor.INSTANCE.toEntity(data);
        return userMapper.updateById(entity);
    }

    @Override
    public int deleteById(Long id) {
        return userMapper.deleteById(id);
    }

}
