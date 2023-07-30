package com.zigaai.authorization.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.validation.group.AddGroup;
import com.zigaai.common.core.model.validation.group.QueryGroup;
import com.zigaai.common.core.model.validation.group.UpdateGroup;
import com.zigaai.authorization.model.dto.command.PagePermissionDTO;
import com.zigaai.authorization.model.dto.query.PagePermissionQuery;
import com.zigaai.authorization.model.vo.PagePermissionVO;
import com.zigaai.authorization.service.PagePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 页面权限表 前端控制器
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Controller
@RequestMapping("/pagePermission")
@RequiredArgsConstructor
public class PagePermissionController {

    private final PagePermissionService pagePermissionService;

    /**
    * 分页查询
    */
    @GetMapping("/page")
    public ResponseData<IPage<PagePermissionVO>> page(@Validated(QueryGroup.class) PagePermissionQuery params) {
        IPage<PagePermissionVO> page = pagePermissionService.page(params);
        return ResponseData.success(page);
    }

    /**
    * 新增
    */
    @PostMapping
    public ResponseData<Integer> add(@Validated(AddGroup.class) PagePermissionDTO data) {
        int count = pagePermissionService.add(data);
        return ResponseData.success(count);
    }

    /**
    * 编辑
    */
    @PutMapping
    public ResponseData<Integer> update(@Validated(UpdateGroup.class) PagePermissionDTO data) {
        int count = pagePermissionService.update(data);
        return ResponseData.success(count);
    }

    /**
    * 根据ID删除
    */
    @DeleteMapping("/{id}")
    public ResponseData<Integer> deleteById(@PathVariable Long id) {
        int count = pagePermissionService.deleteById(id);
        return ResponseData.success(count);
    }

}
