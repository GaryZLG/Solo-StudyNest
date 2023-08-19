package com.garyproject.mooc.interception;

import com.google.common.base.Joiner;

import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.utils.CommonConstants;
import com.garyproject.mooc.utils.ResultMsg;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

  private static final String PATTEN_STATIC = "/static";
  private static final String PATTEN_ERROR = "/error";
  private static final String PATTEN_TARGET = "target";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Map<String, String[]> parameterMap = request.getParameterMap();
    parameterMap.forEach((k, v) -> {
      if (k.equals(ResultMsg.errorMsgKey) || k.equals(ResultMsg.successMsgKey) || k.equals(PATTEN_TARGET)) {
        request.setAttribute(k, Joiner.on(",").join(v));
      }
    });

    String requestURI = request.getRequestURI();
    if (requestURI.startsWith(PATTEN_STATIC) || requestURI.startsWith(PATTEN_ERROR)) {
      return true;
    }

    HttpSession session = request.getSession(true);
    UserAuth userAuth = (UserAuth) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
    if (userAuth != null) {
      UserContext.setUser(userAuth);
    }

    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    UserContext.remove();
  }
}
