package com.zigaai.authorization.model.convertor;

import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.authorization.model.dto.command.UserDTO;
import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.entity.Role;
import com.zigaai.authorization.model.entity.User;
import com.zigaai.authorization.model.security.SystemUser;
import com.zigaai.authorization.model.vo.UserVO;
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
    @Mapping(target = "authorities", expression = "java(com.zigaai.authorization.utils.SecurityUtil.toAuthorities(roleList, permissionList))")
    SystemUser toSystemUser(User user, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList);

}