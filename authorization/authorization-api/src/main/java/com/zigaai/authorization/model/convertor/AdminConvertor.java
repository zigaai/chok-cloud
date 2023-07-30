package com.zigaai.authorization.model.convertor;

import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.authorization.model.dto.command.AdminDTO;
import com.zigaai.authorization.model.entity.Admin;
import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.entity.Role;
import com.zigaai.authorization.model.security.SystemUser;
import com.zigaai.authorization.model.vo.AdminVO;
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
    @Mapping(target = "authorities", expression = "java(com.zigaai.authorization.utils.SecurityUtil.toAuthorities(roleList, permissionList))")
    SystemUser toSystemUser(Admin admin, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList);


}