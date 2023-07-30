package com.zigaai.authorization.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.validation.group.AddGroup;
import com.zigaai.common.core.model.validation.group.QueryGroup;
import com.zigaai.common.core.model.validation.group.UpdateGroup;
import com.zigaai.authorization.model.dto.command.AdminDTO;
import com.zigaai.authorization.model.dto.query.AdminQuery;
import com.zigaai.authorization.model.vo.AdminVO;
import com.zigaai.authorization.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
    * 分页查询
    */
    @GetMapping("/page")
    public ResponseData<IPage<AdminVO>> page(@Validated(QueryGroup.class) AdminQuery params) {
        IPage<AdminVO> page = adminService.page(params);
        return ResponseData.success(page);
    }

    /**
    * 新增
    */
    @PostMapping
    public ResponseData<Integer> add(@Validated(AddGroup.class) AdminDTO data) {
        int count = adminService.add(data);
        return ResponseData.success(count);
    }

    /**
    * 编辑
    */
    @PutMapping
    public ResponseData<Integer> update(@Validated(UpdateGroup.class) AdminDTO data) {
        int count = adminService.update(data);
        return ResponseData.success(count);
    }

    /**
    * 根据ID删除
    */
    @DeleteMapping("/{id}")
    public ResponseData<Integer> deleteById(@PathVariable Long id) {
        int count = adminService.deleteById(id);
        return ResponseData.success(count);
    }

}
