package com.garyproject.mooc.dao;

import com.garyproject.mooc.entity.UserAuth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserAuthDao {
  public List<UserAuth> selectUserAuth();

  /**
   * 增加数据
   * @param userAuth
   * @return
   */
  Integer insert(UserAuth userAuth);

  /**
   * 查询数据
   * @param userAuth
   * @return
   */
  List<UserAuth> query(@Param("userAuth") UserAuth userAuth);

  /**
   * 更新数据
   * @param userAuth
   * @return
   */
  Integer update(@Param("userAuth")UserAuth userAuth);


}
