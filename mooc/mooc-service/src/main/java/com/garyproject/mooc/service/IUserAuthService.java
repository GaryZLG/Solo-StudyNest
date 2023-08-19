package com.garyproject.mooc.service;

import com.garyproject.mooc.entity.UserAuth;

import java.util.List;

public interface IUserAuthService {

  /**
   * 增加数据：注册
   * @param userAuth
   * @return
   */
  UserAuth insert(UserAuth userAuth);

  /**
   * 增加数据：注册
   * @param userAuth
   * @return
   */
  void insert2(UserAuth userAuth);

  /**
   * username是否重复
   * @param username
   * @return
   */
  Boolean getByUsername(String username);

  /**
   * email是否重复
   * @param email
   * @return
   */
  Boolean getByUserEmail(String email);

  /**
   * 用户名密码验证
   * @param username
   * @param password
   * @return
   */
  UserAuth auth(String username, String password);

  /**
   * 更新数据：个人主页
   * @param userAuth
   * @return
   */
  Boolean update(UserAuth userAuth);

  /**
   * 查找信息
   * @param userAuth
   * @return
   */
  List<UserAuth> query(UserAuth userAuth);

  /**
   * token
   * @param userAuth
   * @return
   */
  String authRest(UserAuth userAuth);

  /**
   * 通过userId查找信息
   * @param id
   * @return
   */
  UserAuth getByUserId(Long id);
}
