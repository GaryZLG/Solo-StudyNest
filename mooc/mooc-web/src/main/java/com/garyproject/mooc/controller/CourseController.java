package com.garyproject.mooc.controller;


import com.garyproject.mooc.entity.Comment;
import com.garyproject.mooc.entity.Course;
import com.garyproject.mooc.entity.CourseSectionSub;
import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.entity.UserCourse;
import com.garyproject.mooc.interception.UserContext;
import com.garyproject.mooc.service.ICommentService;
import com.garyproject.mooc.service.ICourseService;
import com.garyproject.mooc.service.imp.IUserCourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {

  @Autowired
  private IUserCourseService iUserCourseService;

  @Autowired
  private ICourseService iCourseService;

  @Autowired
  private ICommentService iCommentService;

  @RequestMapping("/learn")
  public String learn(Long id, ModelMap modelMap) {
    Long userId = UserContext.getUser().getId();
    UserCourse userCourse = new UserCourse();
    userCourse.setCourseId(id);
    userCourse.setUserId(userId);
    List<UserCourse> list = iUserCourseService.query(userCourse);
    if (list.isEmpty()) {
      return "/index";
    }

    List<Course> courseList = iCourseService.query(new Course(id));
    if (courseList.isEmpty()) {
      return "error/404";
    }
    Course course = courseList.get(0);
    modelMap.put("course", course);

    List<CourseSectionSub> chapterSections = iCourseService.queryCourseSection(id);
    if (chapterSections == null || chapterSections.size() == 0) {
      return "error/404";
    }
    modelMap.put("chapterSections", chapterSections);

    return "course/learn";
  }

  @RequestMapping("/learn/comment")
  public String courseComment(Long courseId, ModelMap modelMap) {
    if (courseId == null) {
      return "error/404";
    }

    Comment comment = new Comment();
    comment.setCourseId(courseId);
    List<Comment> list = iCommentService.query(comment);

    modelMap.put("list", list);
    modelMap.put("courseId", courseId);

    return "/course/comment";
  }

  @RequestMapping("/learn/comment/commentPublish")
  @ResponseBody
  public Boolean courseCommentPublish(Comment comment) {
    UserAuth userAuth = UserContext.getUser();
    comment.setUserId(userAuth.getId());
    return iCommentService.insert(comment);
  }
}
