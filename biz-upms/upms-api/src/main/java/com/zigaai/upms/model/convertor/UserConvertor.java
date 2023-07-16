package com.zigaai.upms.model.convertor;

import com.zigaai.upms.model.entity.PagePermission;
import com.zigaai.upms.model.entity.Role;
import com.zigaai.upms.model.enumeration.SysUserType;
import com.zigaai.upms.model.security.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.zigaai.upms.model.dto.command.UserDTO;
import com.zigaai.upms.model.entity.User;
import com.zigaai.upms.model.vo.UserVO;

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
    @Mapping(target = "authorities", expression = "java(com.zigaai.upms.model.utils.SecurityUtil.toAuthorities(roleList, permissionList))")
    SystemUser toSystemUser(User user, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList);

}