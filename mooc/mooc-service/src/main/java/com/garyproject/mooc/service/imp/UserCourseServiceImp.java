package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.dao.UserCourseDao;
import com.garyproject.mooc.entity.UserCourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCourseServiceImp implements IUserCourseService{

  @Autowired
  private UserCourseDao userCourseDao;

  @Override
  public List<UserCourse> query(UserCourse userCourse) {
    List<UserCourse> list = userCourseDao.query(userCourse);
    return list;
  }
}
