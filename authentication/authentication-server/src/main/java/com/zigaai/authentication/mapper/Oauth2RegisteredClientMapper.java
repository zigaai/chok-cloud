package com.zigaai.authentication.mapper;


import com.zigaai.authentication.model.entity.Oauth2RegisteredClient;
import com.zigaai.common.core.infra.mapper.MapperSupport;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-02
 */
public interface Oauth2RegisteredClientMapper extends MapperSupport<Oauth2RegisteredClient> {

    Oauth2RegisteredClient getByClientId(String clientId);

    String getClientIdById(String id);

}
