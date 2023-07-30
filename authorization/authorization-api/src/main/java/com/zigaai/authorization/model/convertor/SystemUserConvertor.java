package com.zigaai.authorization.model.convertor;

import com.zigaai.common.core.model.dto.PayloadDTO;
import com.zigaai.authorization.model.security.SystemUser;
import com.zigaai.authorization.model.vo.SystemUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SystemUserConvertor {

    SystemUserConvertor INSTANCE = Mappers.getMapper(SystemUserConvertor.class);


    @Mapping(target = "iat", ignore = true)
    @Mapping(target = "exp", ignore = true)
    @Mapping(target = "userType", expression = "java(systemUser != null ? systemUser.getUserType().getVal() : null)")
    PayloadDTO toPayloadDTO(SystemUser systemUser, Long duration);

    SystemUserVO toVO(SystemUser systemUser);

}
