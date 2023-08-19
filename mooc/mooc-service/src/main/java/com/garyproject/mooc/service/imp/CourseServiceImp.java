package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.dao.CourseDao;
import com.garyproject.mooc.dao.CourseSectionDao;
import com.garyproject.mooc.entity.Course;
import com.garyproject.mooc.entity.CourseSection;
import com.garyproject.mooc.entity.CourseSectionSub;
import com.garyproject.mooc.service.ICourseService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImp implements ICourseService {

  @Autowired
  private CourseSectionDao courseSectionDao;

  @Autowired
  private CourseDao courseDao;

  @Override
  public List<Course> query(Course course) {
    return courseDao.query(course);
  }


  @Override
  public List<CourseSectionSub> queryCourseSection(Long courseId) {
    List<CourseSectionSub> res = new ArrayList<>();
    CourseSection courseSection = new CourseSection();
    courseSection.setCourseId(courseId);

    Map<Long, CourseSectionSub> map = new LinkedHashMap<>();
    Iterator<CourseSection> iterator = courseSectionDao.query(courseSection).iterator();
    while (iterator.hasNext()) {
      CourseSection item = iterator.next();
      if (Long.valueOf(0).equals(item.getParentId())) {
        CourseSectionSub courseSectionSub = new CourseSectionSub();
        BeanUtils.copyProperties(item, courseSectionSub);
        map.put(courseSectionSub.getId(), courseSectionSub);
      } else {
        map.get(item.getParentId()).getSections().add(item);
      }
    }

    for (CourseSectionSub courseSectionSub : map.values()) {
      res.add(courseSectionSub);
    }

    return res;
  }
}
