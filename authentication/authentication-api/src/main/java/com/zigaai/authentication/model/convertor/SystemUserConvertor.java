package com.zigaai.authentication.model.convertor;

import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.authentication.model.vo.SystemUserVO;
import com.zigaai.common.security.model.dto.PayloadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SystemUserConvertor {

    SystemUserConvertor INSTANCE = Mappers.getMapper(SystemUserConvertor.class);


    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "scopes", ignore = true)
    @Mapping(target = "iat", ignore = true)
    @Mapping(target = "exp", ignore = true)
    PayloadDTO toPayloadDTO(SystemUser systemUser, Long duration);

    SystemUserVO toVO(SystemUser systemUser);

}
