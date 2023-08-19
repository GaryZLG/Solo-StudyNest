package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.UserCourse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserCourseDao {
  /**
   * 查询数据
   * @param userCourse
   * @return
   */
  List<UserCourse> query(@Param("userCourse") UserCourse userCourse);
}
