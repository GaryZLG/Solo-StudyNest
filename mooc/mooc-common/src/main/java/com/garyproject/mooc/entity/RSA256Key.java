package com.garyproject.mooc.entity;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSA256Key {

  private RSAPublicKey publicKey;

  private RSAPrivateKey privateKey;

  public RSA256Key() {
  }

  public RSA256Key(RSAPublicKey publicKey, RSAPrivateCrtKey privateKey) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }
  public RSAPublicKey getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(RSAPublicKey publicKey) {
    this.publicKey = publicKey;
  }

  public RSAPrivateKey getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(RSAPrivateKey privateKey) {
    this.privateKey = privateKey;
  }
}
