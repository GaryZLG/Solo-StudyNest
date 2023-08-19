package com.garyproject.mooc.dao;


import com.garyproject.mooc.entity.CourseSection;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseSectionDao {

  List<CourseSection> query(@Param("courseSection") CourseSection courseSection);
}
