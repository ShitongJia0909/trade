package com.jst.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);

    private static final String ENCODING = "utf-8";
    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";


    public static String sign(String content, String privateKeyStr){
        try{
            PrivateKey priKey = getPrivateKey(privateKeyStr);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(content.getBytes(ENCODING));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed), ENCODING);
        }catch (Exception e){
            throw new RuntimeException("signByPrivateKey error");
        }
    }


    public static boolean verifySign(String content, String sign, String publicKeyStr){
        try{
            PublicKey publicKey = getPublicKey(publicKeyStr);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(content.getBytes(ENCODING));
            return signature.verify(Base64.decodeBase64(sign.getBytes(ENCODING)));
        }catch (Exception e){
            throw new RuntimeException("signBuPrivateKey error");
        }
    }


    private static PrivateKey getPrivateKey(String key) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    private static PublicKey getPublicKey(String key) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
        return publicKey;
    }


}
