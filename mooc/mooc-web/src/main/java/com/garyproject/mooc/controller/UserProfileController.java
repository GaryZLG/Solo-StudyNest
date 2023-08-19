package com.garyproject.mooc.controller;

import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.entity.UserCourse;
import com.garyproject.mooc.interception.UserContext;
import com.garyproject.mooc.service.IUserAuthService;
import com.garyproject.mooc.service.imp.IUserCourseService;
import com.garyproject.mooc.utils.CommonConstants;
import com.garyproject.mooc.utils.ResultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserProfileController {
  @Autowired
  private IUserAuthService iUserAuthService;

  @Autowired
  private IUserCourseService iUserCourseService;

  @RequestMapping("/accounts/profile")
  public String profile(HttpServletRequest request, UserAuth userAuth) {
    if (userAuth.getUsername() == null) {
      return "/accounts/myProfile";
    }
    iUserAuthService.update(userAuth);
    UserAuth query = new UserAuth();
    query.setEmail(userAuth.getEmail());
    List<UserAuth> authList = iUserAuthService.query(userAuth);
    request.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, authList.get(0));
    return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
  }

 @RequestMapping("/accounts/myCourse")
  public String myCourse(ModelMap modelMap) {
    UserAuth userAuth = UserContext.getUser();
    UserCourse userCourse = new UserCourse();
    userCourse.setUserId(userAuth.getId());

    List<UserCourse> userCourseList = iUserCourseService.query(userCourse);
    modelMap.put("userCourse", userCourseList);
    return "/accounts/myCourse";
  }
}
