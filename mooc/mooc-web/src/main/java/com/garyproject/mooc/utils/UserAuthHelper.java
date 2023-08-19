package com.garyproject.mooc.utils;

import com.garyproject.mooc.entity.UserAuth;

import org.apache.commons.lang3.StringUtils;

public class UserAuthHelper {

  public static ResultMsg validate(UserAuth userAuth) {
    if (StringUtils.isBlank(userAuth.getEmail())) {
      return ResultMsg.errorMsg("Email 不能为空");
    }
    if (StringUtils.isBlank(userAuth.getUsername())) {
      return ResultMsg.errorMsg("用户名不能为空");
    }
    if (StringUtils.isBlank(userAuth.getPasswd())
    || StringUtils.isBlank(userAuth.getConfirmPasswd())) {
      return ResultMsg.errorMsg("密码或者确认密码不能为空");
    }
    if(!userAuth.getPasswd().equals(userAuth.getConfirmPasswd())) {
      return ResultMsg.errorMsg("两次密码不一致");
    }
    if(userAuth.getPasswd().length() < 6) {
      return ResultMsg.errorMsg("密码不能小于6位");
    }

    return ResultMsg.successMsg("");
  }
}
