package com.zigaai.authorization.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.validation.group.AddGroup;
import com.zigaai.common.core.model.validation.group.QueryGroup;
import com.zigaai.common.core.model.validation.group.UpdateGroup;
import com.zigaai.authorization.model.dto.command.UserDTO;
import com.zigaai.authorization.model.dto.query.UserQuery;
import com.zigaai.authorization.model.vo.UserVO;
import com.zigaai.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
    * 分页查询
    */
    @GetMapping("/page")
    public ResponseData<IPage<UserVO>> page(@Validated(QueryGroup.class) UserQuery params) {
        IPage<UserVO> page = userService.page(params);
        return ResponseData.success(page);
    }

    /**
    * 新增
    */
    @PostMapping
    public ResponseData<Integer> add(@Validated(AddGroup.class) UserDTO data) {
        int count = userService.add(data);
        return ResponseData.success(count);
    }

    /**
    * 编辑
    */
    @PutMapping
    public ResponseData<Integer> update(@Validated(UpdateGroup.class) UserDTO data) {
        int count = userService.update(data);
        return ResponseData.success(count);
    }

    /**
    * 根据ID删除
    */
    @DeleteMapping("/{id}")
    public ResponseData<Integer> deleteById(@PathVariable Long id) {
        int count = userService.deleteById(id);
        return ResponseData.success(count);
    }

}
