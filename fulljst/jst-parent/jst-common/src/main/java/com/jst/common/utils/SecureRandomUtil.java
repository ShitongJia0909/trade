package com.jst.common.utils;

import java.security.SecureRandom;

public class SecureRandomUtil {

    private static SecureRandom secureRandom;

    static {
        try{
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        }catch (Exception e){
            secureRandom = new SecureRandom();
        }
    }

    public static SecureRandom getRandomInstance() {
        return secureRandom;
    }
}
