package com.zigaai.authentication.model.convertor;

import com.zigaai.authentication.model.vo.RoleVO;
import com.zigaai.authentication.model.dto.command.RoleDTO;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleConvertor {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    RoleDTO toDTO(Role entity);

    RoleVO toVO(Role entity);

    Role toEntity(RoleDTO DTO);

    Role toEntity(RoleVO VO);

}