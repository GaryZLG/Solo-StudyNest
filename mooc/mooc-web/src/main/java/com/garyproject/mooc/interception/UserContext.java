package com.garyproject.mooc.interception;

import com.garyproject.mooc.entity.UserAuth;

import jakarta.servlet.http.HttpServletRequest;

public class UserContext {

  private static final ThreadLocal<UserAuth> USER_HODLER = new ThreadLocal<>();

  public static void setUser(UserAuth userAuth) {
    USER_HODLER.set(userAuth);
  }

  public static void remove() {
    USER_HODLER.remove();
  }

  public static UserAuth getUser() {
    return USER_HODLER.get();
  }

  //静态方法，程序已经启动开始了
  public static void setAttribute(HttpServletRequest request, String key, Object value) {
    request.getSession().setAttribute(key, value);
  }

  public static Object getAttribute(HttpServletRequest request, String key) {
    return request.getSession().getAttribute(key);
  }

}
