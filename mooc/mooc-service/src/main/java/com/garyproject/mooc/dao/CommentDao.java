package com.garyproject.mooc.dao;


import com.garyproject.mooc.entity.Comment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {
  Integer insert(Comment comment);

  List<Comment> query(@Param("comment") Comment comment);

}
