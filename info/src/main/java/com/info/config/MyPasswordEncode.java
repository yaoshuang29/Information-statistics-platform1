package com.info.config;

import com.info.utils.CodecUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncode implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {

        return CodecUtils.passwordBCryptEncode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return CodecUtils.passwordBCryptDecode(rawPassword.toString(),encodedPassword);
    }
}
