package com.zigaai.upms.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.zigaai.upms.model.dto.command.RoleDTO;
import com.zigaai.upms.model.entity.Role;
import com.zigaai.upms.model.vo.RoleVO;

@Mapper
public interface RoleConvertor {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    RoleDTO toDTO(Role entity);

    RoleVO toVO(Role entity);

    Role toEntity(RoleDTO DTO);

    Role toEntity(RoleVO VO);

}