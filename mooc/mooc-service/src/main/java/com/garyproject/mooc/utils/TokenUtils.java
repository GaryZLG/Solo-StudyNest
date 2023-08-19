package com.garyproject.mooc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.garyproject.mooc.entity.RSA256Key;
import com.garyproject.mooc.exception.ConditionException;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class TokenUtils {

  private static final String ISSUER = "签发者";

  public static String generateToken(Long userId) throws NoSuchAlgorithmException {
    RSA256Key rsa256Key = SecretKeyUtil.generateRSA256Key();
    Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());

    //测试，设置20秒后过期
    calendar.add(Calendar.SECOND, 20);
    return JWT.create().withKeyId(String.valueOf(userId))
            .withIssuer(ISSUER)
            .withExpiresAt(calendar.getTime())
            .sign(algorithm);
  }

  public static Long verifyToken(String token) throws NoSuchAlgorithmException {
    try {
      RSA256Key rsa256Key = SecretKeyUtil.generateRSA256Key();
      Algorithm algorithm = Algorithm.RSA256(rsa256Key.getPublicKey(), rsa256Key.getPrivateKey());

      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      String userId = jwt.getKeyId();
      return Long.valueOf(userId);
    } catch (TokenExpiredException e) {
      throw new ConditionException("555","token已过期，请重新登录");
    } catch (Exception e) {
      throw new ConditionException("非法用户");
    }
  }
}
