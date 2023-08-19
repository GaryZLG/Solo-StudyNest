package com.garyproject.mooc.service.imp;


import com.garyproject.mooc.dao.CommentDao;
import com.garyproject.mooc.entity.Comment;
import com.garyproject.mooc.service.ICommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp implements ICommentService {

  @Autowired
  private CommentDao commentDao;

  @Override
  public Boolean insert(Comment comment) {
    if (commentDao.insert(comment) <= 0) {
      return false;
    }
    return true;
  }

  @Override
  public List<Comment> query(Comment comment) {
    return commentDao.query(comment);
  }
}
