package com.zigaai.common.security.utils;

import com.zigaai.common.core.model.constants.SystemConstant;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@UtilityClass
public final class RsaUtil {

    public static KeyPair getKeyPair() {
        return INNER.INSTANCE.KEY_PAIR;
    }

    public static RSAPublicKey getPublicKey() {
        return (RSAPublicKey) getKeyPair().getPublic();
    }

    public static RSAPrivateKey getPrivateKey() {
        return (RSAPrivateKey) getKeyPair().getPrivate();
    }

    private static String generateSecret(Long userId, String salt) {
        return userId + salt;
    }

    private enum INNER {

        INSTANCE;

        private final KeyPair KEY_PAIR;

        INNER() {
            KeyStoreKeyFactory KEY_STORE_KEY_FACTORY = new KeyStoreKeyFactory(new ClassPathResource(SystemConstant.JKS_ALIAS + ".jks"), SystemConstant.JKS_PASSWORD.toCharArray());
            KEY_PAIR = KEY_STORE_KEY_FACTORY.getKeyPair(SystemConstant.JKS_ALIAS);
        }

    }
}
