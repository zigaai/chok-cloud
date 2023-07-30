package com.zigaai.authorization.model.convertor;

import com.zigaai.authorization.model.dto.command.RoleDTO;
import com.zigaai.authorization.model.entity.Role;
import com.zigaai.authorization.model.vo.RoleVO;
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