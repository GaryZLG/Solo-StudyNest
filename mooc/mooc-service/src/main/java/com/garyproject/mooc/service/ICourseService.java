package com.garyproject.mooc.service;

import com.garyproject.mooc.entity.Course;
import com.garyproject.mooc.entity.CourseSectionSub;

import java.util.List;

public interface ICourseService {

  /**
   * 查找信息
   * @param course
   * @return
   */
  List<Course> query(Course course);

  List<CourseSectionSub> queryCourseSection(Long courseId);
}
