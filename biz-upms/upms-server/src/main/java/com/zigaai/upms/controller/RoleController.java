// package com.zigaai.upms.controller;
//
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.stereotype.Controller;
// import lombok.RequiredArgsConstructor;
// import com.baomidou.mybatisplus.core.metadata.IPage;
// import com.zigaai.common.core.model.validation.group.*;
// import com.zigaai.common.core.model.dto.ResponseData;
// import com.zigaai.upms.service.RoleService;
// import com.zigaai.upms.model.vo.RoleVO;
// import com.zigaai.upms.model.dto.query.RoleQuery;
// import com.zigaai.upms.model.dto.command.RoleDTO;
//
// /**
//  * <p>
//  * 角色表 前端控制器
//  * </p>
//  *
//  * @author zigaai
//  * @since 2023-07-15
//  */
// @Controller
// @RequestMapping("/role")
// @RequiredArgsConstructor
// public class RoleController {
//
//     private final RoleService roleService;
//
//     /**
//     * 分页查询
//     */
//     @GetMapping("/page")
//     public ResponseData<IPage<RoleVO>> page(@Validated(QueryGroup.class) RoleQuery params) {
//         IPage<RoleVO> page = roleService.page(params);
//         return ResponseData.success(page);
//     }
//
//     /**
//     * 新增
//     */
//     @PostMapping
//     public ResponseData<Integer> add(@Validated(AddGroup.class) RoleDTO data) {
//         int count = roleService.add(data);
//         return ResponseData.success(count);
//     }
//
//     /**
//     * 编辑
//     */
//     @PutMapping
//     public ResponseData<Integer> update(@Validated(UpdateGroup.class) RoleDTO data) {
//         int count = roleService.update(data);
//         return ResponseData.success(count);
//     }
//
//     /**
//     * 根据ID删除
//     */
//     @DeleteMapping("/{id}")
//     public ResponseData<Integer> deleteById(@PathVariable Long id) {
//         int count = roleService.deleteById(id);
//         return ResponseData.success(count);
//     }
//
// }
