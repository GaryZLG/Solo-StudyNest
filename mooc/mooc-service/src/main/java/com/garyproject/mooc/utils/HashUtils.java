package com.garyproject.mooc.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * MD5加密 + salt 防止明文出现
 */
public class HashUtils {
  private static final HashFunction FUNCTION = Hashing.md5();

  private static final String SALT = "garyproject";

  public static String encryPassword(String password) {
    HashCode hashCode = FUNCTION.hashString(password + SALT, Charset.forName("UTF-8"));
    return hashCode.toString();
  }
}
