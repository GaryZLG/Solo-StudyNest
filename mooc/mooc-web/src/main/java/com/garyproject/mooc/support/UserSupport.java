package com.garyproject.mooc.support;

import com.garyproject.mooc.exception.ConditionException;
import com.garyproject.mooc.utils.TokenUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.NoSuchAlgorithmException;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserSupport {
  public Long getCurrentUserId() throws NoSuchAlgorithmException {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    String token = request.getHeader("token");
    Long userId = TokenUtils.verifyToken(token);
    if (userId < 0) {
      throw new ConditionException("非法用户");
    }
    return userId;
  }
}
