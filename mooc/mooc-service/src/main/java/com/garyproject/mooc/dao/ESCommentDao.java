package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.Comment;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESCommentDao extends ElasticsearchRepository<Comment, Long> {
  //Like不是模糊查询，而是包含查询
  Comment findByContentContaining(String keyword);
}
