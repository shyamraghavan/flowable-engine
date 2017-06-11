package org.flowable.idm.engine.impl.authentication;

import org.flowable.idm.api.PasswordEncoder;

import java.lang.reflect.Method;

public class JasyptPasswordEncryptor implements PasswordEncoder {

    // org.jasypt.util.password.PasswordEncryptor

    private Object encoder;

    public JasyptPasswordEncryptor(Object encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(CharSequence rawPassword, String salt) {
        Method method = loadMethod("encryptPassword", String.class);
        try {
            return (String) method.invoke(encoder, rawPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isMatches(CharSequence rawPassword, String encodedPassword, String salt) {
        Method method = loadMethod("checkPassword", String.class, String.class);
        try {
            return (Boolean) method.invoke(encoder, rawPassword, encodedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Method loadMethod(String methodName, Class... params) {
        try {
            Class<?> aClass = Class.forName("org.jasypt.util.password.PasswordEncryptor");
            return aClass.getMethod(methodName, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
