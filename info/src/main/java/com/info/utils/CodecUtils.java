package com.info.utils;
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CodecUtils {
 
    public static String passwordBCryptEncode(String password){
 
        return new BCryptPasswordEncoder().encode( password);
    }
 
    public static Boolean passwordBCryptDecode(String rawPassword,String encodePassword){
        return new BCryptPasswordEncoder().matches(rawPassword,encodePassword);
    }
}