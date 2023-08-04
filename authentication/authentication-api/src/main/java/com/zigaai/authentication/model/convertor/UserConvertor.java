package com.zigaai.authentication.model.convertor;

import com.zigaai.authentication.model.dto.command.UserDTO;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.entity.User;
import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.authentication.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    UserDTO toDTO(User entity);

    UserVO toVO(User entity);

    User toEntity(UserDTO DTO);

    User toEntity(UserVO VO);

    @Mapping(source = "roleList", target = "roleList")
    @Mapping(source = "permissionList", target = "permissionList")
    @Mapping(target = "authorities", expression = "java(com.zigaai.authentication.utils.SecurityUtil.toAuthorities(roleList, permissionList))")
    @Mapping(target = "userType", expression = "java(com.zigaai.common.core.model.enumeration.SysUserType.USER.getVal())")
    SystemUser toSystemUser(User user, List<Role> roleList, List<PagePermission> permissionList);

}