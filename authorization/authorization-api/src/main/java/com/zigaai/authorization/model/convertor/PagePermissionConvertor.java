package com.zigaai.authorization.model.convertor;

import com.zigaai.authorization.model.dto.command.PagePermissionDTO;
import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.vo.PagePermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PagePermissionConvertor {

    PagePermissionConvertor INSTANCE = Mappers.getMapper(PagePermissionConvertor.class);

    PagePermissionDTO toDTO(PagePermission entity);

    PagePermissionVO toVO(PagePermission entity);

    PagePermission toEntity(PagePermissionDTO DTO);

    PagePermission toEntity(PagePermissionVO VO);

}