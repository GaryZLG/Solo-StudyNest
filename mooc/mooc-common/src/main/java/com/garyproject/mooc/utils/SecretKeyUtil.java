package com.garyproject.mooc.utils;

import com.garyproject.mooc.entity.RSA256Key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class SecretKeyUtil {

  //数字签名，密钥算法
  public static final String KEY_ALGORITHM = "RSA";

  //密钥长度
  public static final int KEY_SIZE = 1024;


  //唯一的密钥实例
  public static volatile RSA256Key rsa256Key;

  /**
   * 生成RSA256密钥对
   * @return
   */
  public static RSA256Key generateRSA256Key() throws NoSuchAlgorithmException {
    if (rsa256Key == null) {
      synchronized (RSA256Key.class) {
        if (rsa256Key == null) {
          KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
          keyPairGenerator.initialize(KEY_SIZE);
          KeyPair keyPair = keyPairGenerator.generateKeyPair();

          RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
          RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

          rsa256Key = new RSA256Key();
          rsa256Key.setPublicKey(publicKey);
          rsa256Key.setPrivateKey(privateKey);
        }
      }
    }
    return rsa256Key;
  }

}
