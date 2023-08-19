package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.entity.UserCourse;

import java.util.List;

public interface IUserCourseService {

  /**
   * 查找信息
   * @param userCourse
   * @return
   */
  List<UserCourse> query(UserCourse userCourse);

}
