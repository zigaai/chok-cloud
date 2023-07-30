// package com.zigaai.upms.model.convertor;
//
// import org.mapstruct.Mapper;
// import org.mapstruct.factory.Mappers;
// import com.zigaai.upms.model.dto.command.PagePermissionDTO;
// import com.zigaai.upms.model.entity.PagePermission;
// import com.zigaai.upms.model.vo.PagePermissionVO;
//
// @Mapper
// public interface PagePermissionConvertor {
//
//     PagePermissionConvertor INSTANCE = Mappers.getMapper(PagePermissionConvertor.class);
//
//     PagePermissionDTO toDTO(PagePermission entity);
//
//     PagePermissionVO toVO(PagePermission entity);
//
//     PagePermission toEntity(PagePermissionDTO DTO);
//
//     PagePermission toEntity(PagePermissionVO VO);
//
// }