package com.zigaai.authorization.model.dto.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class PrincipalKey implements Serializable {

    private final String principalName;

    private final String authorizationId;

}
