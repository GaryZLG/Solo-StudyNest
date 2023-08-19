package com.garyproject.mooc.controller;

import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.service.IUserAuthService;
import com.garyproject.mooc.service.UserAuthService;
import com.garyproject.mooc.utils.CommonConstants;
import com.garyproject.mooc.utils.ResultMsg;
import com.garyproject.mooc.utils.UserAuthHelper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserAuthController {
  @Autowired
  private IUserAuthService iUserAuthService;

  /**
   * 注册
   * @param userAuth
   * @param modelMap
   * @return
   */
  @RequestMapping("/register")
  public String register(UserAuth userAuth, ModelMap modelMap) {
    if (userAuth == null || userAuth.getEmail() == null) {
      return "auth/register";
    }
    ResultMsg resultMsg = UserAuthHelper.validate(userAuth);

    if (iUserAuthService.getByUsername(userAuth.getUsername())) {
      resultMsg.setErrorMsg("用户名已注册");
    }

    if (iUserAuthService.getByUserEmail(userAuth.getEmail())) {
      resultMsg.setErrorMsg("邮箱已注册");
    }

    if (resultMsg.isSuccess() && iUserAuthService.insert(userAuth)) {
      modelMap.put("email", userAuth.getEmail());
      return "redirect:/registerSubmit?email=" + userAuth.getEmail();
    } else {
      return "redirect:/register?" + resultMsg.asUrlParams();
    }
  }

  /**
   * 注册成功
   * @param email
   * @param modelMap
   * @return
   */
  @RequestMapping("registerSubmit")
  public String registerSubmit(String email, ModelMap modelMap) {
    modelMap.put("email", email);
    return "auth/registerSubmit";
  }

  @RequestMapping("/login")
  public String login(HttpServletRequest request, ModelMap modelMap) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String target = request.getParameter("target");
    if (username == null || password == null) {
      return "auth/login";
    }

    UserAuth userAuth = iUserAuthService.auth(username, password);
    if (userAuth == null) {
      return "redirect:/login?" + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
    } else {
      HttpSession session = request.getSession(true);
      session.setAttribute(CommonConstants.USER_ATTRIBUTE, userAuth);
      return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
    }
  }

  @RequestMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    session.invalidate();
    return "redirect:/index";
  }

}
