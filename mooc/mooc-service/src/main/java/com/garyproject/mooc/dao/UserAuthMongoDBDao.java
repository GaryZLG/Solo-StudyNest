package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.UserAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthMongoDBDao {
  @Autowired
  private MongoTemplate mongoTemplate;

  public void insert(UserAuth userAuth) {
    mongoTemplate.insert(userAuth);
  }
}
