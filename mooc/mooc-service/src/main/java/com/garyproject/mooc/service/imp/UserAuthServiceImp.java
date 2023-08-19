package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.dao.UserAuthDao;
import com.garyproject.mooc.dao.UserAuthMongoDBDao;
import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.service.IUserAuthService;
import com.garyproject.mooc.utils.HashUtils;
import com.garyproject.mooc.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class UserAuthServiceImp implements IUserAuthService {

  @Autowired
  private UserAuthDao userAuthDao;

  @Autowired
  private UserAuthMongoDBDao userAuthMongoDBDao;

  @Override
  public UserAuth insert(UserAuth userAuth) {
    userAuth.setPasswd(HashUtils.encryPassword(userAuth.getPasswd()));
    userAuth.setCreateTime(new Date());
    if (userAuthDao.insert(userAuth) <= 0) {
      return null;
    }

    return getUserAuthByQuery(userAuth).get(0);
  }

  @Override
  public void insert2(UserAuth userAuth) {
    userAuthMongoDBDao.insert(userAuth);
  }

  @Override
  public Boolean getByUsername(String username) {
    UserAuth userAuth = new UserAuth();
    userAuth.setUsername(username);
    List<UserAuth> list = userAuthDao.query(userAuth);
    return !list.isEmpty();
  }

  @Override
  public Boolean getByUserEmail(String email) {
    UserAuth userAuth = new UserAuth();
    userAuth.setUsername(email);
    List<UserAuth> list = userAuthDao.query(userAuth);
    return !list.isEmpty();
  }

  @Override
  public UserAuth auth(String username, String password) {
    UserAuth userAuth = new UserAuth();
    userAuth.setUsername(username);
    userAuth.setPasswd(HashUtils.encryPassword(password));
    List<UserAuth> list = getUserAuthByQuery(userAuth);
    if (!list.isEmpty()) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public Boolean update(UserAuth userAuth) {
    if (userAuthDao.update(userAuth) <= 0) {
      return false;
    }
    return true;
  }

  @Override
  public List<UserAuth> query(UserAuth userAuth) {
    List<UserAuth> list = userAuthDao.query(userAuth);
    return list;
  }

  @Override
  public String authRest(UserAuth userAuth) {
    List<UserAuth> list = getUserAuthByQuery(userAuth);
    if (!list.isEmpty()) {
      userAuth = list.get(0);
      try {
        return TokenUtils.generateToken(userAuth.getId());
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  @Override
  public UserAuth getByUserId(Long id) {
    UserAuth userAuth = new UserAuth();
    userAuth.setId(id);
    List<UserAuth> list = userAuthDao.query(userAuth);
    return list.get(0);
  }

  private List<UserAuth> getUserAuthByQuery(UserAuth userAuth) {
    List<UserAuth> list = userAuthDao.query(userAuth);
    return list;
  }
}
