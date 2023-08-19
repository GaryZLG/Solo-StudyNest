package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.dao.CourseSectionDao;
import com.garyproject.mooc.entity.CourseSection;
import com.garyproject.mooc.service.ICourseSectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSectionServiceImp implements ICourseSectionService {
  @Autowired
  private CourseSectionDao courseSectionDao;

  @Override
  public List<CourseSection> query(CourseSection courseSection) {
    return courseSectionDao.query(courseSection);
  }
}
