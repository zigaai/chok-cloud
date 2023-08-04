package com.zigaai.authentication.model.convertor;

import com.zigaai.authentication.model.dto.command.AdminDTO;
import com.zigaai.authentication.model.entity.Admin;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.authentication.model.vo.AdminVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminConvertor {

    AdminConvertor INSTANCE = Mappers.getMapper(AdminConvertor.class);

    AdminDTO toDTO(Admin entity);

    @Mapping(target = "roleList", ignore = true)
    @Mapping(target = "pagePermissionList", ignore = true)
    AdminVO toVO(Admin entity);

    Admin toEntity(AdminDTO DTO);

    @Mapping(target = "password", ignore = true)
    Admin toEntity(AdminVO VO);

    @Mapping(source = "roleList", target = "roleList")
    @Mapping(source = "permissionList", target = "permissionList")
    @Mapping(target = "authorities", expression = "java(com.zigaai.authentication.utils.SecurityUtil.toAuthorities(roleList, permissionList))")
    @Mapping(target = "userType", expression = "java(com.zigaai.common.core.model.enumeration.SysUserType.ADMIN.getVal())")
    SystemUser toSystemUser(Admin admin, List<Role> roleList, List<PagePermission> permissionList);


}