package com.garyproject.mooc.service;

import com.garyproject.mooc.entity.CourseSection;

import java.util.List;

public interface ICourseSectionService {

  /**
   * 查找信息
   * @param userCourse
   * @return
   */
  List<CourseSection> query(CourseSection userCourse);
}
