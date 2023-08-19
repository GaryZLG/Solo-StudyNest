package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.UserAuth;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESUserAuthDao extends ElasticsearchRepository<UserAuth, Long> {

  UserAuth findByUsernameLike(String username);
}
