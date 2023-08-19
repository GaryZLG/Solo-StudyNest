package com.garyproject.mooc.controller;

import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.service.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TestEmailController {
  @Autowired
  private UserAuthService userAuthService;

  @RequestMapping("testEmail")
  public String testEmail(ModelMap modelMap) {
    List<UserAuth> userAuthList = userAuthService.getUserAuth();
    UserAuth userAuth = userAuthList.get(0);
    if (userAuth != null) {
      throw new IllegalArgumentException();
    }
    modelMap.put("user", userAuth);
    return "testEmail";
  }
}
