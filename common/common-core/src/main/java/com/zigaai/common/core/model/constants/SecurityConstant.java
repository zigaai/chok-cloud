package com.zigaai.common.core.model.constants;

import java.util.concurrent.TimeUnit;

public final class SecurityConstant {

    private SecurityConstant() {
    }

    public static final String PRE_AUTHORIZATION_HEADER = "Pre-Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final long REFRESH_DURATION = TimeUnit.MINUTES.toMillis(5);

    public static final class TokenKey {

        public static final String ID = "id";

        public static final String USER_TYPE = "userType";

        public static final String CLIENT_ID = "clientId";

        public static final String KID = "kid";

        public static final String SID = "sid";

        public static final String SUB = "sub";

        public static final String EXP = "exp";

        public static final String IAT = "iat";

        public static final String AUD = "aud";

        public static final String SCOPE = "scope";
    }

}
