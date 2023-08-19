package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.Course;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseDao {
  /**
   * 查询数据
   * @param course
   * @return
   */
  List<Course> query(@Param("course")Course course);
}
